package com.example.zaitoneh.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.ReceiptDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class ReceiptDetailViewModelFactory(
    private val receiptKey: Long,
    private val dataSource: ReceiptDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceiptDetailViewModel::class.java)) {
            return ReceiptDetailViewModel(receiptKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
