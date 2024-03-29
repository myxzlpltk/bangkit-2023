package com.dicoding.myrecyclerview.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myrecyclerview.HeroActivity
import com.dicoding.myrecyclerview.R
import com.dicoding.myrecyclerview.model.Hero
import androidx.core.util.Pair as UtilPair

/**
 * Created by sidiqpermana on 10/28/16.
 */

class ListHeroAdapter(private val listHero: ArrayList<Hero>) :
    RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHero[position])
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.profileImageView)
        private var tvName: TextView = itemView.findViewById(R.id.nameTextView)
        private var tvDescription: TextView = itemView.findViewById(R.id.descTextView)

        fun bind(hero: Hero) {
            Glide.with(itemView.context).load(hero.photo).circleCrop().into(imgPhoto)
            tvName.text = hero.name
            tvDescription.text = hero.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, HeroActivity::class.java)
                intent.putExtra("Hero", hero)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    UtilPair(imgPhoto, "profile"),
                    UtilPair(tvName, "name"),
                    UtilPair(tvDescription, "description")
                )

                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

}
