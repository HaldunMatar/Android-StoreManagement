/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package  com.example.zaitoneh.receipttracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.marsrealestate.network.ItemApiFilter
import com.example.android.marsrealestate.network.StoreApi
import com.example.zaitoneh.database.Receipt
import com.example.zaitoneh.database.ReceiptDatabaseDao
import com.example.zaitoneh.database.Supplier
import com.example.zaitoneh.itemtracker.ItemApiStatus
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class ReceiptTrackerViewModel(
    val database: ReceiptDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val _navigateToEditReceipt = MutableLiveData<Long>()
    val navigateToEditReceipt
        get() = _navigateToEditReceipt


    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var latestReceipt = MutableLiveData<Receipt?>()

    val receipts = database.getAllReceipts()

    /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    /**
     * Variable that tells the Fragment to navigate to a specific [SleepQualityFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToReceipt = MutableLiveData<Receipt>()
    /**
     * Call this immediately after calling `show()` on a toast.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
    /**
     * If this is non-null, immediately navigate to [SleepQualityFragment] and call [doneNavigating]
     */
    val navigateToSleepQuality: LiveData<Receipt>
        get() = _navigateToReceipt

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToReceipt.value = null
    }

    init {
        initializeLatestReceipt()

        _navigateToEditReceipt.value=null
        getReceiptsNet(ItemApiFilter.SHOW_ALL)
    }

    private fun initializeLatestReceipt() {
        uiScope.launch {
           latestReceipt.value = getToLatestReceiptFromDatabase()
        }
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getToLatestReceiptFromDatabase(): Receipt? {
        return withContext(Dispatchers.IO) {
            var receipt = database.getlatestReceipt()

            receipt
        }

    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(receipt: Receipt) {
        withContext(Dispatchers.IO) {
            database.update(receipt)
        }
    }



    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        uiScope.launch {
            // Clear the database table.
            clear()
            // And clear tonight since it's no longer in the database
            latestReceipt.value = null
        }

        // Show a snackbar message, because it's friendly.
        _showSnackbarEvent.value = true
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onReceiptClicked(receiptId: Long?) {
        Log.i("onclick","onReceiptClicked")
         _navigateToEditReceipt.value=receiptId
    }

    // The external immutable LiveData for the request status
    private val _status = MutableLiveData<ItemApiStatus>()
    val status: LiveData<ItemApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _list = MutableLiveData<List<Receipt>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val list: LiveData<List<Receipt>>
        get() = _list

    private fun getReceiptsNet(filter: ItemApiFilter) {
        Log.i("getReceiptsNet"," before ");

        uiScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = StoreApi.retrofitService.getReceipts()
            try {
                _status.value = ItemApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = ItemApiStatus.DONE
                Log.i("getReceiptssNet"," DONE " + listResult.size);
                _list.value = listResult
            } catch (e: Exception) {
                Log.i("getReceiptssNet",e.message);
                _status.value = ItemApiStatus.ERROR
                _list.value = ArrayList()
            }
        }
    }




}
