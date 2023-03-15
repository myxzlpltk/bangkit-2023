package com.example.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val items = fileList()

        AlertDialog.Builder(this).apply {
            setTitle("Pilih file yang diinginkan")
            setItems(items) { _, item -> loadData(items[item].toString()) }
        }.create().show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.read(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading ${fileModel.filename} data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(
                this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT
            ).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(
                this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT
            ).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel = FileModel(title, text)

                FileHelper.write(fileModel, this)
                Toast.makeText(this, "Saving ${fileModel.filename} file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}