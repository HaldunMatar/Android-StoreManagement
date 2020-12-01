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

package  com.example.zaitoneh.employeetracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.ItemApiFilter
import com.example.android.marsrealestate.network.StoreApi
import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.database.EmployeeDatabaseDao
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.itemtracker.ItemApiStatus
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class EmployeeTrackerViewModel() : ViewModel() {

    private val _navigateToEditEmployee = MutableLiveData<Long>()
         val navigateToEditEmployee
         get() = _navigateToEditEmployee

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

    private var latestEmployee = MutableLiveData<Employee?>()

   // val employees = database.getAllEmployees()

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

    private val _navigateToEmployee = MutableLiveData<Employee>()
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
    val navigateToSleepQuality: LiveData<Employee>
        get() = _navigateToEmployee

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToEmployee.value = null
    }
    private var lateEmployee = MutableLiveData<Store?>()
    init {

        initializeLatestEmployee()

   Log.i("initializeEmployees","before")
        _navigateToEditEmployee.value=null
        getEmployeesNet(ItemApiFilter.SHOW_ALL)
        Log.i("initializeEmployees","after")

    }
    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    val _list = MutableLiveData<List<Employee>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val list: LiveData<List<Employee>>
        get() = _list

    fun getEmployeesNet(filter: ItemApiFilter) {
        Log.i("getEmployeesNet"," before ");

        uiScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = StoreApi.retrofitService.getEmployees()
            try {
                _status.value = ItemApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = ItemApiStatus.DONE
                Log.i("getEmployeesNet"," DONE " + listResult.size);
                _list.value = listResult
                } catch (e: Exception) {
                Log.i("getEmployeessNet",e.message);
                _status.value = ItemApiStatus.ERROR
                _list.value = ArrayList()
            }
        }
    }
    private fun initializeLatestEmployee() {
        uiScope.launch {
           latestEmployee.value = getToLatestEmployeeFromDatabase()
        }
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getToLatestEmployeeFromDatabase(): Employee? {
        return withContext(Dispatchers.IO) {
          //  var employee = database.getlatestEmployee()
            var employee = Employee()
            employee
        }

    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
           // database.clear()
        }
    }

    private suspend fun update(employee: Employee) {
        withContext(Dispatchers.IO) {
           // database.update(employee)
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
            latestEmployee.value = null
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

    fun onEmployeeClicked(employeeId: Long?) {
        Log.i("onclick","onEmployeeClicked"+ employeeId.toString())
         _navigateToEditEmployee.value=employeeId
    }

    // The external immutable LiveData for the request status
    private val _status = MutableLiveData<ItemApiStatus>()
    val status: LiveData<ItemApiStatus>
        get() = _status



}
