package com.example.mynoteappswithroom.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mynoteappswithroom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository = NoteRepository(application)

    fun getAll() = mNoteRepository.getAll()
}