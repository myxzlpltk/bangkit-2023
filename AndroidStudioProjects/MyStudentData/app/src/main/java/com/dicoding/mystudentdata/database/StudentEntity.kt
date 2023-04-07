package com.dicoding.mystudentdata.database

import androidx.room.*

@Entity
data class Student(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int,
    @ColumnInfo(defaultValue = "false")
    val isGraduate: Boolean? = false
)

@Entity
data class University(
    @PrimaryKey
    val universityId: Int,
    val name: String,
)

@Entity
data class Course(
    @PrimaryKey
    val courseId: Int,
    val name: String,
)

data class StudentAndUniversity(
    @Embedded
    val student: Student,

    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId",
    )
    val university: University? = null,
)

data class UniversityAndStudents(
    @Embedded
    val university: University,

    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId",
    )
    val students: List<Student>,
)

@Entity(primaryKeys = ["sId", "cId"])
data class CourseStudentCrossRef(
    val sId: Int,
    @ColumnInfo(index = true)
    val cId: Int,
)

data class StudentWithCourses(
    @Embedded
    val studentAndUniversity: StudentAndUniversity,

    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val courses: List<Course>,
)

data class CourseWithStudents(
    @Embedded
    val course: Course,

    @Relation(
        parentColumn = "courseId",
        entity = Student::class,
        entityColumn = "studentId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "cId",
            entityColumn = "sId"
        )
    )
    val students: List<Student>
)