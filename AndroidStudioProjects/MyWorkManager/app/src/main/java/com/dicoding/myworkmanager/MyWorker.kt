package com.dicoding.myworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.msebera.android.httpclient.Header
import java.text.DecimalFormat

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private val TAG = MyWorker::class.java.simpleName
        const val API_KEY = BuildConfig.API_KEY
        const val API_URL = BuildConfig.API_URL
        const val EXTRA_CITY = "city"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "Dicoding Channel"
    }

    private var resultStatus: Result? = null

    override fun doWork(): Result {
        val city = inputData.getString(EXTRA_CITY)

        return if (city == null) Result.failure()
        else getCurrentWeather(city)
    }

    private fun getCurrentWeather(city: String): Result {
        Log.d(TAG, "getCurrentWeather: Mulai.....")
        if (Looper.myLooper() == null) Looper.prepare()
        val client = SyncHttpClient()
        val url = Uri.parse(API_URL).buildUpon().run {
            appendQueryParameter("q", city)
            appendQueryParameter("appid", API_KEY)
            toString()
        }
        Log.d(TAG, "getCurrentWeather: $url")

        client.post(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter = moshi.adapter(Response::class.java)
                    val response = jsonAdapter.fromJson(result)

                    response?.let {
                        val currentWeather = it.weatherList[0].main
                        val description = it.weatherList[0].description
                        val tempInKelvin = it.main.temperature
                        val tempInCelsius = tempInKelvin - 273
                        val temperature: String = DecimalFormat("##.##").format(tempInCelsius)
                        val title = "Current Weather in $city"
                        val message = "$currentWeather, $description with $temperature celsius"

                        showNotification(title, message)
                    }
                    Log.d(TAG, "onSuccess: Selesai.....")
                    resultStatus = Result.success()
                } catch (error: Exception) {
                    showNotification("Get Current Weather Not Success", error.message)
                    Log.d(TAG, "onSuccess: Gagal.....")
                    resultStatus = Result.failure()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable,
            ) {
                Log.d(TAG, "onFailure: Gagal.....")
                showNotification("Get Current Weather Failed", error.message)
                resultStatus = Result.failure()
            }
        })

        return resultStatus as Result
    }

    private fun showNotification(title: String, message: String?) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_notifications_black_24)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_HIGH
                setDefaults(NotificationCompat.DEFAULT_ALL)
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH,
                )
            )
        }

        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}