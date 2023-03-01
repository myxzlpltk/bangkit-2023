package com.example.favoritemovies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favoritemovies.databinding.ItemRowMovieBinding

class ListMovieAdapter(private val listMovie: ArrayList<Movie>) :
    RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {

    init {
        listMovie.sortByDescending { movie: Movie -> movie.rating }
    }

    class ListViewHolder(var binding: ItemRowMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        Glide.with(holder.itemView).load(listMovie[i].poster).into(holder.binding.itemPoster)
        holder.binding.itemTitle.text = listMovie[i].title
        holder.binding.itemRating.rating = listMovie[i].rating.toFloat()
        holder.binding.movieItem.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailMovieActivity::class.java)
            intentDetail.putExtra(
                DetailMovieActivity.EXTRA_MOVIE,
                listMovie[holder.adapterPosition]
            )
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}