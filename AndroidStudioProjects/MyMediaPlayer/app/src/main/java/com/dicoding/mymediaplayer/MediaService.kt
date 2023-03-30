package com.dicoding.mymediaplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import java.lang.ref.WeakReference

class MediaService : Service(), MediaPlayerCallback {

    companion object {
        const val ACTION_CREATE = "MediaService@create"
        const val ACTION_DESTROY = "MediaService@destroy"
        const val TAG = "MediaService"
        const val PLAY = 0
        const val STOP = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
        private const val NOTIFICATION_ID = 1
    }

    private var isReady = false
    private var mMediaPlayer: MediaPlayer? = null
    private val mMessenger = Messenger(IncomingHandler(this))

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        when (intent?.action) {
            ACTION_CREATE -> if (mMediaPlayer == null) init()
            ACTION_DESTROY -> if (mMediaPlayer?.isPlaying == true) stopSelf()
            else -> init()
        }
        return flags
    }

    override fun onBind(intent: Intent): IBinder {
        return mMessenger.binder
    }

    private fun init() {
        val afd = applicationContext.resources.openRawResourceFd(R.raw.deja_vu)
        val audioAttrs = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setAudioAttributes(audioAttrs)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            it.start()
            showNotif()
        }
        mMediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }

    override fun onPlay() {
        if (!isReady) {
            mMediaPlayer?.prepareAsync()
        } else {
            if (mMediaPlayer?.isPlaying == true) {
                mMediaPlayer?.pause()
            } else {
                mMediaPlayer?.start()
                showNotif()
            }
        }
    }

    override fun onStop() {
        if (mMediaPlayer?.isPlaying == true || isReady) {
            mMediaPlayer?.stop()
            isReady = false
            stopNotif()
        }
    }

    internal class IncomingHandler(cb: MediaPlayerCallback) : Handler(Looper.getMainLooper()) {
        private val mediaPlayerCallbackWeakReference = WeakReference(cb)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PLAY -> mediaPlayerCallbackWeakReference.get()?.onPlay()
                STOP -> mediaPlayerCallbackWeakReference.get()?.onStop()
                else -> super.handleMessage(msg)
            }
        }
    }

    private fun showNotif() {
        // Create intent
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Create notification
        val mNotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TES1")
            .setContentText("TES2")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setTicker("TES3")

        // Create channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(false)
            channel.setSound(null, null)
            notificationBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopNotif() {
        stopForeground(STOP_FOREGROUND_DETACH)
    }
}