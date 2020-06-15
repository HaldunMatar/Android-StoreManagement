package com.example.zaitoneh.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.ItemDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class ItemDetailViewModelFactory(
    private val itemKey: Long,
    private val dataSource: ItemDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
            return ItemDetailViewModel(itemKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
