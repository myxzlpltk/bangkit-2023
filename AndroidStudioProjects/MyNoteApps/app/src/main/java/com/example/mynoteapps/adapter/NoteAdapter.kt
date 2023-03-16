package com.example.mynoteapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynoteapps.R
import com.example.mynoteapps.databinding.ItemNoteBinding
import com.example.mynoteapps.entity.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listNotes = ArrayList<Note>()

    fun setList(newList: ArrayList<Note>) {
        if (newList.size > 0) listNotes.clear()
        newList.forEach { note -> addItem(note) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    fun addItem(note: Note) {
        listNotes.add(note)
        notifyItemInserted(listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listNotes.size)
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvItemTitle.text = note.title
            binding.tvItemDate.text = note.date
            binding.tvItemDescription.text = note.description
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }
    }
}