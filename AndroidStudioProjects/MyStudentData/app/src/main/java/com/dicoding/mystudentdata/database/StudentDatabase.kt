package com.dicoding.mystudentdata.database

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executors

@Database(
    entities = [Student::class, University::class, Course::class, CourseStudentCrossRef::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = StudentDatabase.MyAutoMigration2To3::class),
    ],
    exportSchema = true,
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, StudentDatabase::class.java, "student_database"
                    ).run {
                        fallbackToDestructiveMigration()
                        setQueryCallback(object : QueryCallback {
                            override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                                Log.i("SQL Query", "$sqlQuery SQL Args: $bindArgs")
                            }
                        }, Executors.newSingleThreadExecutor())
                        createFromAsset("student_database.db")
                        /*addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        database.studentDao().apply {
                                            insertStudent(InitialDataSource.getStudents())
                                            insertUniversity(InitialDataSource.getUniversities())
                                            insertCourse(InitialDataSource.getCourses())
                                            insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
                                        }
                                    }
                                }
                            }
                        })*/
                        build()
                    }
                }
            }
            return INSTANCE as StudentDatabase
        }

    }

    @RenameColumn("Student", "graduate", "isGraduate")
    class MyAutoMigration2To3 : AutoMigrationSpec
}