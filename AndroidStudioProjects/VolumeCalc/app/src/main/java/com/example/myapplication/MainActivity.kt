package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener(this)

        if (savedInstanceState != null) {
            binding.textResult.text = savedInstanceState.getString(STATE_RESULT)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.textResult.text.toString())
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button_calculate) {
            if (!validate(binding.editLength) || !validate(binding.editWidth) || !validate(binding.editHeight)) return

            val inputHeight = binding.editHeight.text.toString().trim().toDouble()
            val inputLength = binding.editLength.text.toString().trim().toDouble()
            val inputWidth = binding.editWidth.text.toString().trim().toDouble()

            val volume = inputHeight * inputLength * inputWidth
            binding.textResult.text = volume.toString()
        }
    }

    private fun validate(field: EditText): Boolean {
        return if (field.text.trim().isEmpty()) {
            field.error = "Field ini tidak boleh kosong"
            false
        } else {
            true
        }
    }
}