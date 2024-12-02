package com.samant.rommdummy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samant.rommdummy.adapters.NoteDeletedRVAdapter
import com.samant.rommdummy.databinding.FragmentHistoryragmentBinding
import com.samant.rommdummy.viewmodel.NoteViewModel


class Historyragment : Fragment() {
    private var _binding: FragmentHistoryragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var notesRV: RecyclerView
    private lateinit var noteDeletedRVAdapter: NoteDeletedRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryragmentBinding.inflate(inflater, container, false)
        initView()

        notesRV.layoutManager = LinearLayoutManager(requireContext())
        noteDeletedRVAdapter = NoteDeletedRVAdapter(requireContext())
        notesRV.adapter = noteDeletedRVAdapter

        viewModel.allDeletedNotes.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if (it.isEmpty()) {
                    binding.recyclerView.visibility = View.GONE
                    binding.lnrNoTask.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.lnrNoTask.visibility = View.GONE
                    noteDeletedRVAdapter.updateList(it)
                }
            }
        })
        return binding.root
    }

    private fun initView() {
        notesRV = binding.recyclerView
    }


    companion object {
      const val TAG = "HomeFragment"
    }
}