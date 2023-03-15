package com.example.myreadwritefile

import android.content.Context

internal object FileHelper {

    fun write(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data.trim().toByteArray())
        }
    }

    fun read(context: Context, filename: String): FileModel {
        return FileModel(
            filename,
            context.openFileInput(filename).bufferedReader().useLines {
                it.fold("") { some, text -> "$some\n$text" }
            }.trim(),
        )
    }
}