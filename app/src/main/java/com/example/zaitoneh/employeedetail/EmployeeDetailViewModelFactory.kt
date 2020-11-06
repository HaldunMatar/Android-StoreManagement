package com.example.zaitoneh.employeedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.EmployeeDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class EmployeeDetailViewModelFactory(
    private val smployeeKey: String?,
    private val dataSource: EmployeeDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeDetailViewModel::class.java)) {
            return EmployeeDetailViewModel(smployeeKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
