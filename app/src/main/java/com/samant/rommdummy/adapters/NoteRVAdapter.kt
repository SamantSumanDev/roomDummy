package com.samant.rommdummy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.R
import javax.inject.Inject

class NoteRVAdapter @Inject constructor(
    private val noteClickInterface: NoteClickInterface,
    private val noteShareClickInterface: NoteClickShareInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    interface NoteClickShareInterface {
        fun onShareIconClick(note: Note)
    }

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        val tvDesc: AppCompatTextView = itemView.findViewById(R.id.tvDesc)
        val tvTime: AppCompatTextView = itemView.findViewById(R.id.tvTime)
        val imgShare: AppCompatImageView = itemView.findViewById(R.id.imgShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = allNotes[position]

        holder.tvTitle.text = currentItem.noteTitle
        holder.tvDesc.text = currentItem.noteDescription
        holder.tvTime.text = currentItem.timeStamp

        holder.imgShare.setOnClickListener {
            noteShareClickInterface.onShareIconClick(currentItem)
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(currentItem)
        }
    }

    override fun getItemCount(): Int = allNotes.size

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}





