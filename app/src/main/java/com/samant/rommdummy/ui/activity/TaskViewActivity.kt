package com.samant.rommdummy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.R
import com.samant.rommdummy.databinding.ActivityTaskViewBinding
import com.samant.rommdummy.viewmodel.NoteViewModel

class TaskViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskViewBinding
    private lateinit var note: Note
    private val viewModel: NoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setClickListner()
        dataListner()
    }

    private fun dataListner() {

        if (intent.hasExtra("note")) {
            note = intent.getParcelableExtra<Note>("note")!!
            val name = note?.noteTitle
            val description = note?.noteDescription
            val dateTime = note?.timeStamp

            binding.include2.tvAction.text= name
            binding.tvTitle.text = name
            binding.tvDesc.text = description
            binding.tvTime.text = dateTime
            binding.tvTime.text = dateTime


        }
    }

    private fun setClickListner() {
        binding.include2.appCompatImageView2.setImageResource(R.drawable.back)
        binding.include2.appCompatImageView2.setOnClickListener {
            finish()
        }
        binding.lnrShare.setOnClickListener {
            shareNote(note)
        }

        binding.imgShare.setOnClickListener {
            if (note !=null){
                shareNote(note)
            }
        }
        binding.lnrEdit.setOnClickListener {
            openEditTask(note)
        }

        binding.lnrEdit.setOnClickListener {
            openEditTask(note)
        }
        binding.lnrDelete.setOnClickListener {

            viewModel.deleteNoteAndRecord(note)
            Toast.makeText(this,"Task Deleted",Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.imgDelete.setOnClickListener {
            Log.e("TaskViewACT",note.toString())
            viewModel.deleteNoteAndRecord(note)
                Toast.makeText(this,"Task Deleted",Toast.LENGTH_SHORT).show()
                finish()

        }
    }
    fun openEditTask(note: Note){
        val intent = Intent(this, AddNewTaskActivity::class.java)
        intent.putExtra("noteType", "edit")
        intent.putExtra("note", note)
        startActivity(intent)
    }

    private fun shareNote(note:Note) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getNoteShareText(note))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun getNoteShareText(note:Note): String {
        return "Note: ${note.noteTitle}\n" +
                "Description: ${note.noteDescription}\n" +
                "Venue: ${note.noteMapLink}\n" +
                "Map Link: ${note.noteMapLink}\n" +
                "Timestamp: ${note.timeStamp}"
    }
}