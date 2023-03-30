package com.dicoding.myplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL_VIDEO =
            "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO =
            "https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        if (player == null) initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build().apply {
            binding.videoView.player = this
            setMediaItem(MediaItem.fromUri(URL_VIDEO))
            addMediaItem(MediaItem.fromUri(URL_AUDIO))
            prepare()
            play()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }
}