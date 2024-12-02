package com.samant.rommdummy.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.adapters.NoteRVAdapter
import com.samant.rommdummy.databinding.FragmentHomeBinding
import com.samant.rommdummy.ui.activity.TaskViewActivity
import com.samant.rommdummy.viewmodel.NoteViewModel

class HomeFragment : Fragment(), NoteRVAdapter.NoteClickInterface, NoteRVAdapter.NoteClickShareInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var notesRV: RecyclerView
    private lateinit var noteRVAdapter: NoteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initView() {
        notesRV = binding.recyclerView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()
        notesRV.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter
        noteRVAdapter = NoteRVAdapter(requireContext(), this,this)

        // Set adapter to RecyclerView
        notesRV.adapter = noteRVAdapter

        // Observe changes on the list
        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if (it.isEmpty()) {
                    binding.recyclerView.visibility = View.GONE
                    binding.lnrNoTask.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.lnrNoTask.visibility = View.GONE
                    noteRVAdapter.updateList(it)
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }


    private fun shareNote(note: Note) {
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
                "Date-Time: ${note.timeStamp}"
    }


    fun openTaskView(note: Note){
        val intent = Intent(activity, TaskViewActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    override fun onNoteClick(note: Note) {
        openTaskView(note)
    }

    override fun onShareIconClick(note: Note) {
        shareNote(note)
    }
}
