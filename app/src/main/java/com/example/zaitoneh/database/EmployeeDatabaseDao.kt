package com.example.zaitoneh.database


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDatabaseDao{
    @Insert
    fun insert(employee:Employee)

    @Update
    fun update(employee:Employee)

    @Query("DELETE FROM employee_table WHERE employeeId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of employee that matches the supplied key
     */
    @Query("SELECT * from employee_table WHERE employeeId = :key")
    fun get(key: Long): Employee?


    @Query("DELETE FROM employee_table")
    fun clear()

    @Query("SELECT * FROM employee_table ORDER BY employeeId DESC")
    fun getAllEmployees(): LiveData<List<Employee>>

    /**
     * Selects and returns the employee with given employee Id.
     */
    @Query("SELECT * from employee_table WHERE employeeId = :key")
    fun getEmployeeWithId(key: Long): LiveData<Employee>


    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM employee_table ORDER BY employeeId DESC LIMIT 1")
    fun getlatestEmployee(): Employee?

    @Query("SELECT * FROM employee_table ORDER BY employeeId DESC")
    fun getEmployees(): List<Employee>
}