package com.example.mynoteappswithroom.repository

import android.app.Application
import com.example.mynoteappswithroom.database.Note
import com.example.mynoteappswithroom.database.NoteRoomDatabase
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao = NoteRoomDatabase.getDatabase(application).noteDao()
    private val executorService = Executors.newSingleThreadExecutor()

    fun getAll() = mNotesDao.getAll()

    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}