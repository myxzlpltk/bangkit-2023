package com.dicoding.mystudentdata

import androidx.lifecycle.LiveData
import com.dicoding.mystudentdata.database.*

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(): LiveData<List<Student>> = studentDao.getAllStudent()
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> =
        studentDao.getAllStudentAndUniversity()

    fun getAllUniversityAndStudents(): LiveData<List<UniversityAndStudents>> =
        studentDao.getAllUniversityAndStudents()

    fun getAllStudentWithCourses(): LiveData<List<StudentWithCourses>> =
        studentDao.getAllStudentWithCourses()
}