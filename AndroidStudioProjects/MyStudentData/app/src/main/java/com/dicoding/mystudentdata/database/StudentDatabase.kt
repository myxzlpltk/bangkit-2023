package com.dicoding.mystudentdata.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

@Database(
    entities = [Student::class, University::class, Course::class, CourseStudentCrossRef::class], version = 1, exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, StudentDatabase::class.java, "student_database"
                    ).fallbackToDestructiveMigration().apply {
                        setQueryCallback(object : QueryCallback {
                            override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                                Log.i("SQL Query", "$sqlQuery SQL Args: $bindArgs")
                            }
                        }, Executors.newSingleThreadExecutor())
                    }.build()
                }
            }
            return INSTANCE as StudentDatabase
        }

    }
}