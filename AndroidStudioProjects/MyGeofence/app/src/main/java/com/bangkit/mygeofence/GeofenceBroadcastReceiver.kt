package com.bangkit.mygeofence

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

            if (geofencingEvent.hasError()) {
                val message = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                Log.e(TAG, message)
                sendNotification(context, message)
                return
            }

            when (val geofenceTransition = geofencingEvent.geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER,
                Geofence.GEOFENCE_TRANSITION_DWELL,
                -> {
                    val geofenceTransitionString =
                        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                            "Anda telah memasuki area"
                        } else {
                            "Anda telah di dalam area"
                        }

                    val triggeringGeofences = geofencingEvent.triggeringGeofences
                    val requestId = triggeringGeofences?.get(0)?.requestId ?: return
                    val geofenceTransitionDetails = "$geofenceTransitionString $requestId"
                    Log.i(TAG, geofenceTransitionDetails)
                    sendNotification(context, geofenceTransitionDetails)
                }
                else -> {
                    val errorMessage = "Invalid transition type : $geofenceTransition"
                    Log.e(TAG, errorMessage)
                    sendNotification(context, errorMessage)
                }
            }
        }
    }

    private fun sendNotification(context: Context, geofenceTransitionDetails: String) {
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(geofenceTransitionDetails)
            .setContentText("Anda sudah bisa absen sekarang :)")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiver"
        const val ACTION_GEOFENCE_EVENT = "GeofenceEvent"
        /* Notification */
        private const val CHANNEL_ID = "1"
        private const val CHANNEL_NAME = "Geofence Channel"
        private const val NOTIFICATION_ID = 1
    }
}