package com.example.zaitoneh.database


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DepartmentDatabaseDao{
    @Insert
    fun insert(department:Department)

    @Update
    fun update(department:Department)

    @Query("DELETE FROM department_table WHERE departmentId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of department that matches the supplied key
     */
    @Query("SELECT * from department_table WHERE departmentId = :key")
    fun get(key: Long): Department?

  @Query("DELETE FROM department_table")
    fun clear()

    @Query("SELECT * FROM department_table ORDER BY departmentId DESC")
    fun getAllDepartments(): LiveData<List<Department>>

    /**
     * Selects and returns the department with given department Id.
     */
    @Query("SELECT * from department_table WHERE departmentId = :key")
    fun getDepartmentWithId(key: Long): LiveData<Department>


    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM department_table ORDER BY departmentId DESC LIMIT 1")
    fun getlatestDepartment(): Department?

    @Query("SELECT * FROM department_table ORDER BY departmentId DESC")
    fun getDepartments(): List<Department>
}