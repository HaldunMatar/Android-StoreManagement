package com.example.zaitoneh.departmentdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.DepartmentDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class DepartmentDetailViewModelFactory(
    private val departmentKey: Long,
    private val dataSource: DepartmentDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DepartmentDetailViewModel::class.java)) {
            return DepartmentDetailViewModel(departmentKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
