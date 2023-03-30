package com.dicoding.mysound

import android.annotation.SuppressLint
import android.media.SoundPool
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mysound.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var mSoundPool: SoundPool
    private var soundId = 0
    private var spLoaded = false
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Setup sound
        mSoundPool = SoundPool.Builder().setMaxStreams(1).build()
        mSoundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@MainActivity, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }
        soundId = mSoundPool.load(this, R.raw.kaget, 1)

        // Setup action
        setupAction()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupAction() {
        binding.layout.setOnTouchListener { _, motionEvent ->
            if (spLoaded && motionEvent.action == MotionEvent.ACTION_DOWN) {
                binding.popowiScore.text = "${++score}"
                mSoundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                binding.popowiImage.setImageResource(R.drawable.wah)
                true
            } else if (spLoaded && motionEvent.action == MotionEvent.ACTION_UP) {
                binding.popowiImage.setImageResource(R.drawable.u)
                true
            } else {
                false
            }
        }
    }
}