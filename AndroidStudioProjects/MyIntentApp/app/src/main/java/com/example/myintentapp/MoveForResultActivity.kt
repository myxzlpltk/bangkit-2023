package com.example.myintentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MoveForResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnChoose: Button
    private lateinit var rgNumber: RadioGroup

    companion object {
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_for_result)

        btnChoose = findViewById(R.id.btn_choose)
        rgNumber = findViewById(R.id.rg_number)

        btnChoose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_choose && rgNumber.checkedRadioButtonId > 0) {
            val value: Int = when (rgNumber.checkedRadioButtonId) {
                R.id.rb_50 -> 50
                R.id.rb_100 -> 100
                R.id.rb_150 -> 150
                R.id.rb_200 -> 200
                else -> 0
            }

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SELECTED_VALUE, value)
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }
}