package com.samant.rommdummy.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gamdestroyerr.roomnote.model.Note
import com.samant.rommdummy.R
import kotlin.random.Random

class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteShareClickInterface:NoteClickShareInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    interface NoteClickShareInterface {

        fun onShareIconClick(note: Note)
    }

    interface NoteClickInterface {

        fun onNoteClick(note: Note)
    }

    private val allNotes = ArrayList<Note>()

    // on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<AppCompatTextView>(R.id.tvTitle)
        val tvDesc = itemView.findViewById<AppCompatTextView>(R.id.tvDesc)
        val tvTime = itemView.findViewById<AppCompatTextView>(R.id.tvTime)
        val imgShare = itemView.findViewById<AppCompatImageView>(R.id.imgShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        val currentItem = allNotes[position]

        holder.tvTitle.setText(currentItem.noteTitle)
        holder.tvDesc.setText(currentItem.noteDescription)
        holder.tvTime.setText(currentItem.timeStamp)

        holder.imgShare.setOnClickListener {
            noteShareClickInterface.onShareIconClick(allNotes.get(position))
        }

        holder.itemView.setOnClickListener {
           noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {

        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}




