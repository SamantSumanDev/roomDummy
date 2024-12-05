package com.samant.rommdummy.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.R
import com.samant.rommdummy.databinding.ActivityAddNewTaskBinding
import com.samant.rommdummy.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewTaskBinding
    private val viewModel: NoteViewModel by viewModels()

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        initUI()
        handleIntent()
        setupClickListeners()
    }

    private fun setupWindowInsets() {
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {
        binding.include2.appCompatImageView2.apply {
            setImageResource(R.drawable.back)
            setOnClickListener { finish() }
        }
        binding.include2.tvAction.text = "Add New Task"
        binding.tvDateTIme.text = getCurrentDateTime()
    }

    private fun handleIntent() {
        val noteType = intent.getStringExtra("noteType")
        if (noteType == "edit") {
            val note = intent.getParcelableExtra<Note>("note")!!
            id = note.id
            populateFields(note)
            binding.btnSave.text = "Update Task"
        } else {
            binding.btnSave.text = "Save Task"
        }
    }

    private fun populateFields(note: Note) {
        binding.apply {
            edtEventName.setText(note.noteTitle)
            edtEventDescription.setText(note.noteDescription)
            edtAddress.setText(note.noteVenue)
            edtMapLink.setText(note.noteMapLink)
            tvDateTIme.text = note.timeStamp
        }
    }

    private fun setupClickListeners() {
        binding.viewEdit.setOnClickListener { showDateTimePicker() }
        binding.imgEdit.setOnClickListener { showDateTimePicker() }

        binding.btnSave.setOnClickListener { handleSaveOrUpdate() }
    }

    private fun handleSaveOrUpdate() {
        val name = binding.edtEventName.text.toString()
        val description = binding.edtEventDescription.text.toString()
        val address = binding.edtAddress.text.toString()
        val mapLink = binding.edtMapLink.text.toString()
        val dateTime = binding.tvDateTIme.text.toString()

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please Fill Empty Fields", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(id, name, description, address, mapLink, dateTime)

        if (binding.btnSave.text == "Update Task") {
            viewModel.updateNote(note)
            Toast.makeText(this, "Task Updated on ID: $id", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addNote(note)
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        val dateFormat = SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
                        binding.tvDateTIme.text = dateFormat.format(calendar.time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000
        }.show()
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
}
