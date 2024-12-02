package com.samant.rommdummy.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.R
import com.samant.rommdummy.databinding.ActivityAddNewTaskBinding
import com.samant.rommdummy.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddNewTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewTaskBinding
    private lateinit var edtEventName: AppCompatEditText
    private lateinit var edtDescription: AppCompatEditText
    private lateinit var edtAddress: AppCompatEditText
    private lateinit var edtMapLink: AppCompatEditText
    private lateinit var viewEdit: View
    private lateinit var imgEdit: AppCompatImageView
    private var id: Int = 0
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        clickListner()

    }

    private fun clickListner() {
        binding.include2.appCompatImageView2.setImageResource(R.drawable.back)
        binding.include2.appCompatImageView2.setOnClickListener {
            finish()
        }
        val currentDateTime = getCurrentDateTime()
        binding.tvDateTIme.text = currentDateTime
        binding.include2.tvAction.text = "Add New Task"
        binding.viewEdit.setOnClickListener {
            showDateTimePicker()
        }

        binding.imgEdit.setOnClickListener {
            showDateTimePicker()
        }

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("edit")) {
            val note = intent.getParcelableExtra<Note>("note")!!
            id = note.id
            edtEventName.setText(note.noteTitle)
            edtDescription.setText(note.noteDescription)
            edtAddress.setText(note.noteVenue)
            edtMapLink.setText(note.noteMapLink)
            binding.tvDateTIme.setText(note.timeStamp)

            binding.btnSave.text = "Update Task"
        } else {
            binding.btnSave.text = "Save Task"
        }

        binding.btnSave.setOnClickListener {

            val name = edtEventName.text.toString()
            val description = edtDescription.text.toString()
            val address = edtAddress.text.toString()
            val mapLink = edtMapLink.text.toString()
            val dateTime = binding.tvDateTIme.text.toString()

            if (noteType.equals("edit")) {
                if (name.isNotEmpty() && description.isNotEmpty()) {


                   viewModel.updateNote(
                        id = id,
                        noteTitle = name,
                        noteDescription = description,
                        noteVenue = address,
                        noteMapLink = mapLink,
                        timeStamp = dateTime
                    )
                    Toast.makeText(this, "Task Updated on ID: $id", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please Fill Empty Fields", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (name.isNotEmpty() && description.isNotEmpty()) {
                    viewModel.addNote(Note(noteTitle =  name, noteDescription =  description, noteVenue = address, noteMapLink =  mapLink, timeStamp =  dateTime))
                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please Fill Empty Fields", Toast.LENGTH_SHORT).show()
                }

            }


        }


    }



    private fun initView() {
        edtEventName = binding.edtEventName
        edtDescription = binding.edtEventDescription
        edtAddress = binding.edtAddress
        edtMapLink = binding.edtMapLink
        viewEdit = binding.viewEdit
        imgEdit = binding.imgEdit
    }


    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val hour = selectedDate.get(Calendar.HOUR_OF_DAY)
                val minute = selectedDate.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDate.set(Calendar.MINUTE, minute)

                        val dateFormat =
                            SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
                        val dateTime = dateFormat.format(selectedDate.time)
                        binding.tvDateTIme.text = dateTime
                        //   Toast.makeText(this, "Selected Date and Time: $dateTime", Toast.LENGTH_LONG).show()
                    },
                    hour,
                    minute,
                    false
                )

                timePickerDialog.show()
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
}