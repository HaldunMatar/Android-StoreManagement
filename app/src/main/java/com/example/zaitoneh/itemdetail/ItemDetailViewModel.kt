package com.example.zaitoneh.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.database.ItemDatabaseDao
import com.example.zaitoneh.database.Item
import kotlinx.coroutines.*

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class ItemDetailViewModel(
    private val itemKey: Long = 0L,
    dataSource: ItemDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its ItemDatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val item = MediatorLiveData<Item>()

    fun getItem() = item

    init {
        item.addSource(database.getItemWithId(itemKey), item::setValue)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [ItemTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToItemTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [ItemTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToItemTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    /**
     * Call this immediately after navigating to [ItemTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToItemTracker.value = null
    }

    fun onClose() {
        _navigateToItemTracker.value = true
    }

    private suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            database.insert(item)
        }
    }
    fun onCreateItem(newitem:Item) {
        uiScope.launch {
            // Create a new item,
            // and insert it into the database.

            insert(newitem)

            //item.value = getTonightFromDatabase()
        }
    }



}

