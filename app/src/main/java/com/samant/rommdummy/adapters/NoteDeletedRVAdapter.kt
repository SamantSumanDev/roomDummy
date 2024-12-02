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
import com.samant.rommdummy.R
import com.samant.rommdummy.model.DeletedNote
import kotlin.random.Random

class NoteDeletedRVAdapter(
    val context: Context
) :
    RecyclerView.Adapter<NoteDeletedRVAdapter.ViewHolder>() {



    private val allDeletedNotes = ArrayList<DeletedNote>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<AppCompatTextView>(R.id.tvTitle)
        val tvDesc = itemView.findViewById<AppCompatTextView>(R.id.tvDesc)
        val tvTime = itemView.findViewById<AppCompatTextView>(R.id.tvTime)
        val imgComplete = itemView.findViewById<AppCompatImageView>(R.id.imgShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = allDeletedNotes[position]

        holder.tvTitle.setText(currentItem.noteTitle)
        holder.tvDesc.setText(currentItem.noteDescription)
        holder.tvTime.setText(currentItem.timeStamp)
        holder.imgComplete.setImageResource(R.drawable.done)

    }

    override fun getItemCount(): Int {

        return allDeletedNotes.size
    }

    fun updateList(newList: List<DeletedNote>) {
        allDeletedNotes.clear()
        allDeletedNotes.addAll(newList)
        notifyDataSetChanged()
    }
}




