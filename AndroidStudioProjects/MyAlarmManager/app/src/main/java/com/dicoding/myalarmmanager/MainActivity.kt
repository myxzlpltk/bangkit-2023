package com.dicoding.myalarmmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myalarmmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener one time alarm
        binding.btnOnceDate.setOnClickListener(this)
        binding.btnOnceTime.setOnClickListener(this)
        binding.btnSetOnceAlarm.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            R.id.btn_once_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            R.id.btn_set_once_alarm -> {
                val onceDate = binding.tvOnceDate.text.toString()
                val onceTime = binding.tvOnceTime.text.toString()
                val onceMessage = binding.edtOnceMessage.text.toString()

                alarmReceiver.setOneTimeAlarm(
                    this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage
                )
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, date: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, date)
        val dateFormat = SimpleDateFormat(AlarmReceiver.DATE_FORMAT, Locale.getDefault())

        binding.tvOnceDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat(AlarmReceiver.TIME_FORMAT, Locale.getDefault())
        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding.tvOnceTime.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> {}
            else -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}