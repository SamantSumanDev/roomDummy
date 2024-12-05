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
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.adapters.NoteRVAdapter
import com.samant.rommdummy.databinding.FragmentHomeBinding
import com.samant.rommdummy.ui.activity.TaskViewActivity
import com.samant.rommdummy.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), NoteRVAdapter.NoteClickInterface, NoteRVAdapter.NoteClickShareInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteRVAdapter: NoteRVAdapter

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerView()
        observeNotes()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteRVAdapter
        }
    }

    private fun observeNotes() {
        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.lnrNoTask.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.lnrNoTask.visibility = View.GONE
                noteRVAdapter.updateList(list)
            }
        })
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(activity, TaskViewActivity::class.java).apply {
            putExtra("note", note)
        }
        startActivity(intent)
    }

    override fun onShareIconClick(note: Note) {
        val shareText = getNoteShareText(note)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun getNoteShareText(note: Note): String {
        return """
            Note: ${note.noteTitle}
            Description: ${note.noteDescription}
            Venue: ${note.noteMapLink}
            Map Link: ${note.noteMapLink}
            Date-Time: ${note.timeStamp}
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
