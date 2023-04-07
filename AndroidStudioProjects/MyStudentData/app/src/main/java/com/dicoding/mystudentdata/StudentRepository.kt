package com.dicoding.mystudentdata

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.mystudentdata.database.*
import com.dicoding.mystudentdata.helper.SortType
import com.dicoding.mystudentdata.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(sortType: SortType): LiveData<PagedList<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        val students = studentDao.getAllStudent(query)
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(true)
            setInitialLoadSizeHint(30)
            setPageSize(10)
        }.build()

        return LivePagedListBuilder(students, config).build()
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> =
        studentDao.getAllStudentAndUniversity()

    fun getAllUniversityAndStudents(): LiveData<List<UniversityAndStudents>> =
        studentDao.getAllUniversityAndStudents()

    fun getAllStudentWithCourses(): LiveData<List<StudentWithCourses>> =
        studentDao.getAllStudentWithCourses()
}