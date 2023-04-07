package com.dicoding.mystudentdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mystudentdata.database.UniversityAndStudents
import com.dicoding.mystudentdata.databinding.ItemStudentBinding

class UniversityAndStudentsAdapter :
    ListAdapter<UniversityAndStudents, UniversityAndStudentsAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UniversityAndStudents) {
            with(binding) {
                tvItemName.text = data.students.joinToString(", ") { it.name }
                tvItemUniversity.text = data.university.name
                tvItemUniversity.visibility = View.VISIBLE
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<UniversityAndStudents>() {
        override fun areItemsTheSame(
            oldItem: UniversityAndStudents,
            newItem: UniversityAndStudents,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: UniversityAndStudents,
            newItem: UniversityAndStudents,
        ): Boolean {
            return oldItem.university.name == newItem.university.name
        }
    }
}