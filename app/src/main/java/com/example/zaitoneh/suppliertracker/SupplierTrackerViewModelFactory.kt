package com.example.zaitoneh.suppliertracker


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.SupplierDatabaseDao
import com.example.zaitoneh.suppliertracker.SupplierTrackerViewModel
/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class SupplierTrackerViewModelFactory(
    private val dataSource: SupplierDatabaseDao,

    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupplierTrackerViewModel::class.java)) {
            return SupplierTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

