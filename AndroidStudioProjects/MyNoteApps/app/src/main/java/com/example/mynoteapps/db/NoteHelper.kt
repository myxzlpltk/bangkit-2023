package com.example.mynoteapps.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mynoteapps.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynoteapps.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {

    private var databaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper = INSTANCE ?: synchronized(this) {
            NoteHelper(context).also { INSTANCE = it }
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) database.close()
    }

    fun queryAll(): Cursor = database.query(
        DATABASE_TABLE, null, null, null, null, null, "$_ID ASC",
    )

    fun queryById(id: String): Cursor = database.query(
        DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null,
    )

    fun insert(values: ContentValues?): Long = database.insert(DATABASE_TABLE, null, values)

    fun update(id: String, values: ContentValues?): Int =
        database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))

    fun deleteById(id: String): Int = database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
}