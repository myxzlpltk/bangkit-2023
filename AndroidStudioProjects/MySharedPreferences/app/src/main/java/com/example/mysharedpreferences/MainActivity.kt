package com.example.mysharedpreferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mysharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityMainBinding

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null && result.resultCode == FormUserPreferenceActivity.RESULT_CODE) {
            userModel =
                result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
            populateView(userModel)
            checkForm(userModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My User Preference"
        mUserPreference = UserPreference(this)
        showExistingPreference()

        binding.btnSave.setOnClickListener(this)
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text = userModel.name.ifEmpty { "Tidak Ada" }
        binding.tvAge.text = userModel.age.toString().ifEmpty { "Tidak Ada" }
        binding.tvIsLoveMu.text = if (userModel.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = userModel.email.ifEmpty { "Tidak Ada" }
        binding.tvPhone.text = userModel.phoneNumber.ifEmpty { "Tidak Ada" }
    }

    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            intent.putExtra(
                FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                if (isPreferenceEmpty) FormUserPreferenceActivity.TYPE_ADD else FormUserPreferenceActivity.TYPE_EDIT
            )
            intent.putExtra("USER", userModel)
            resultLauncher.launch(intent)
        }
    }

}