package com.dicoding.mymediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mymediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupMediaPlayer()

        binding.btnPlay.setOnClickListener {
            if (!isReady) {
                mMediaPlayer?.prepareAsync()
            } else {
                if (mMediaPlayer?.isPlaying == true) {
                    mMediaPlayer?.pause()
                } else {
                    mMediaPlayer?.start()
                }
            }
        }

        binding.btnStop.setOnClickListener {
            if (mMediaPlayer?.isPlaying == true || isReady) {
                mMediaPlayer?.stop()
                isReady = false
            }
        }
    }

    private fun setupMediaPlayer() {
        val afd = applicationContext.resources.openRawResourceFd(R.raw.deja_vu)
        val audioAttrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(audioAttrs)
            try {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setOnPreparedListener {
                isReady = true
                it.start()
            }
            setOnErrorListener { _, _, _ -> false }
        }
    }
}