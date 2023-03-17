package com.dicoding.myworkmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.dicoding.myworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workManager = WorkManager.getInstance(this)

        binding.btnOneTimeTask.setOnClickListener(this)
        binding.btnPeriodicTask.setOnClickListener(this)
        binding.btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    private fun startOneTimeTask() {
        binding.textStatus.text = getString(R.string.status)
        val data = Data.Builder().run {
            putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
            build()
        }
        val constraint = Constraints.Builder().run {
            setRequiredNetworkType(NetworkType.CONNECTED)
            build()
        }
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).run {
            setInputData(data)
            setConstraints(constraint)
            build()
        }

        workManager.enqueue(oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this@MainActivity) {
            binding.textStatus.append("\n${it.state.name}")
        }
    }

    private fun startPeriodicTask() {
        binding.textStatus.text = getString(R.string.status)
        val data = Data.Builder().run {
            putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
            build()
        }
        val constraint = Constraints.Builder().run {
            setRequiredNetworkType(NetworkType.CONNECTED)
            build()
        }
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES).run {
            setInputData(data)
            setConstraints(constraint)
            build()
        }

        workManager.enqueue(periodicWorkRequest)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this@MainActivity) {
            binding.textStatus.append("\n${it.state.name}")
            binding.btnCancelTask.isEnabled = it.state == WorkInfo.State.ENQUEUED
        }
    }

    private fun cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.id)
    }
}