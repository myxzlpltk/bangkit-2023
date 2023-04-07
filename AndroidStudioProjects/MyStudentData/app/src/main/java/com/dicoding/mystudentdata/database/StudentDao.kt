package com.dicoding.mystudentdata.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: List<Student>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUniversity(university: List<University>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: List<Course>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourseStudentCrossRef(courseStudentCrossRef: List<CourseStudentCrossRef>)

    @Query("SELECT * from student")
    fun getAllStudent(): LiveData<List<Student>>

    @Transaction
    @Query("SELECT * FROM student")
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>>

    @Transaction
    @Query("SELECT * from university")
    fun getAllUniversityAndStudents(): LiveData<List<UniversityAndStudents>>

    @Transaction
    @Query("SELECT * from student")
    fun getAllStudentWithCourses(): LiveData<List<StudentWithCourses>>
}