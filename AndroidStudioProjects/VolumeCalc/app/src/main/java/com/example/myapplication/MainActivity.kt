package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editHeight: EditText
    private lateinit var editLength: EditText
    private lateinit var editWidth: EditText
    private lateinit var btnCalculate: Button
    private lateinit var textResult: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editHeight = findViewById(R.id.editTextHeight)
        editLength = findViewById(R.id.editTextLength)
        editWidth = findViewById(R.id.editTextWidth)
        btnCalculate = findViewById(R.id.buttonCalculate)
        textResult = findViewById(R.id.textViewResult)

        btnCalculate.setOnClickListener(this)

        if (savedInstanceState != null) {
            textResult.text = savedInstanceState.getString(STATE_RESULT)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, textResult.text.toString())
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonCalculate) {
            if (!validate(editLength) || !validate(editWidth) || !validate(editHeight)) return

            val inputHeight = editHeight.text.toString().trim().toDouble()
            val inputLength = editLength.text.toString().trim().toDouble()
            val inputWidth = editWidth.text.toString().trim().toDouble()

            val volume = inputHeight * inputLength * inputWidth
            textResult.text = volume.toString()
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