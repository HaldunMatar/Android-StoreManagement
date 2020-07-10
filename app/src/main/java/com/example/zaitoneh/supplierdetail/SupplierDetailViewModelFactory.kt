package com.example.zaitoneh.supplierdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.SupplierDatabaseDao
import com.example.zaitoneh.supplierdetail.SupplierDetailViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class SupplierDetailViewModelFactory(
    private val supplierKey: Long,
    private val dataSource: SupplierDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupplierDetailViewModel::class.java)) {
            return SupplierDetailViewModel(supplierKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
