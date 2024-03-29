package com.dicoding.newsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.newsapp.R
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.databinding.ItemNewsBinding
import com.dicoding.newsapp.ui.NewsAdapter.MyViewHolder
import com.dicoding.newsapp.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) :
    ListAdapter<NewsEntity, MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class MyViewHolder(
        private val binding: ItemNewsBinding,
        val onBookmarkClick: (NewsEntity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            binding.ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.ivBookmark.context,
                    if (news.isBookmarked) R.drawable.ic_bookmarked_white else R.drawable.ic_bookmark_white
                )
            )
            Glide.with(itemView.context).load(news.urlToImage).apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
            ).into(binding.imgPoster)
            binding.ivBookmark.setOnClickListener { onBookmarkClick(news) }
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}