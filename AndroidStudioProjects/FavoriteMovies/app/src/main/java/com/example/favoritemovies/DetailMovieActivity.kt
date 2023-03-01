package com.example.favoritemovies

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.example.favoritemovies.databinding.ActivityDetailMovieBinding


class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Get extra data */
        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MOVIE)
        } as Movie

        /* Setup view */
        title = movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /* Setup content */
        Glide.with(this).load(movie.poster).into(binding.moviePoster)
        binding.movieTitle.text = movie.title
        binding.movieGenre.text = movie.genres.joinToString(", ")
        binding.movieRating.rating = movie.rating.toFloat()
        binding.movieYear.text = movie.year.toString()
        binding.movieLanguage.text = movie.language
        binding.movieLength.text = getString(R.string.runtime, movie.runtime)
        binding.moviePlot.text = movie.plot
        binding.movieRated.text = movie.rated
        binding.movieStars.text = movie.actors.joinToString(", ")
        binding.movieDirector.text = movie.director
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if (item.itemId == R.id.action_share) share()

        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val bitmap = binding.moviePoster.drawable.toBitmap()
        val bitmapPath: String =
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, "poster", "")
        val bitmapUri: Uri = Uri.parse(bitmapPath)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this movie! The title is ${movie.title}")
        startActivity(Intent.createChooser(intent, "Share"))
    }
}