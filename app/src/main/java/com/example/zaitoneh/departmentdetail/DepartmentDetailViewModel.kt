package com.example.zaitoneh.departmentdetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.R
import com.example.zaitoneh.database.DepartmentDatabaseDao
import com.example.zaitoneh.database.Department
///import kotlinx.android.synthetic.main.fragment_department_detail.view.*
import kotlinx.coroutines.*
import java.lang.Exception



class DepartmentDetailViewModel(
    private val departmentKey: Long = 0L,
    dataSource: DepartmentDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its DepartmentDatabaseDao.
     */
    val database = dataSource


    //= MutableLiveData<Department?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val department = MediatorLiveData<Department>()

    var department1 = Department()
    private val _saveDepartmentToDataBase = MutableLiveData<Boolean?>()
    val saveDepartmentToDataBase: LiveData<Boolean?>
        get() = _saveDepartmentToDataBase


    private val _deleteDepartmentFromDataBase = MutableLiveData<Boolean?>()
    val deleteDepartmentFromDataBase: LiveData<Boolean?>
        get() = _deleteDepartmentFromDataBase


    private val _updateDepartmentToDataBase = MutableLiveData<Boolean?>()
    val updateDepartmentToDataBase: LiveData<Boolean?>
        get() = _updateDepartmentToDataBase


    private val _departmentValidation = MutableLiveData<Boolean?>()
    val departmentValidation: LiveData<Boolean?>
        get() = _departmentValidation

    fun getDepartment() = department


    init {
        department.addSource(database.getDepartmentWithId(departmentKey), department::setValue)
        //  _departmentValidation.value=false
    }


    /**
     * Variable that tells the fragment whether it should navigate to [DepartmentTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToDepartmentTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [DepartmentTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToDepartmentTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("departmentdetails", " onCleared v M ")
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [DepartmentTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToDepartmentTracker.value = null
    }

    fun onClose() {
        _navigateToDepartmentTracker.value = true
    }

    private suspend fun insert(department: Department) {
        withContext(Dispatchers.IO) {
            database.insert(department)

        }
    }

    private suspend fun update(department: Department) {
        withContext(Dispatchers.IO) {
            database.update(department)
            Log.i("update", department.departmentId.toString())
        }
    }


    fun vaildateDepartment(newDepartment: Department): Boolean {
        return !false
        Log.i("vaildateDepartment", " inside vaildateDepartment")
    }


    fun onCreateDepartment(newDepartment: Department) {
        Log.i("vaildateDepartment", " onCreateDepartment")
        Log.i("viewModeldepartment", newDepartment.toString())

        newDepartment.departmentId = this.departmentKey

        if (vaildateDepartment(newDepartment)) {
            Log.i("vaildateDepartment", " if  onCreateDepartment")
            uiScope.launch {
                try {

                    if (departmentKey == 0L) {
                        insert(newDepartment)
                        _saveDepartmentToDataBase.value = true
                    } else {
                        Log.i("update", "update")
                        update(newDepartment)
                        _updateDepartmentToDataBase.value = true

                    }

                } catch (E: Exception) {
                    Log.i("Exception", "There is a problem in insert same department added")
                    if (newDepartment.departmentId == 0L)
                        _saveDepartmentToDataBase.value = false
                    else
                        _updateDepartmentToDataBase.value = false
                }
            }
        } else {
            _departmentValidation.value = false
        }
    }

    fun OnDeleteDepartment() {

        uiScope.launch {

            try {
                Log.i("delete", department.value!!.departmentId.toString())
                department!!.value?.departmentId?.let { deleteDepartment(it) }
                _deleteDepartmentFromDataBase.value = true

            } catch (E: Exception) {
                Log.i("Exception", "The department was not deleted")
                _deleteDepartmentFromDataBase.value = false

            }
        }
    }

    private suspend fun deleteDepartment(departmentId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(departmentId)

        }
    }


    fun setSaveDepartmentToDataBase() {
        _saveDepartmentToDataBase.value = true

    }


    fun setupdateDepartmentToDataBase() {
        _updateDepartmentToDataBase.value = true
    }

    fun getDepartment(id: Long) {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                getDepartmentFromDB(id)
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(1) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getDepartmentFromDB(id: Long): Department {
        // simulate long running task
        var department = database.get(id)!!
        this.department1 = department;
        return department
    }

 lateinit  var emps :List<Department>

    suspend fun getDepartmentsDB(): List<Department> {

        return database.getDepartments()
    }

    fun getDepartments() {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                 emps=  getDepartmentsDB()
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(200) // keeping jvm alive till calculateSum is finished
        }
    }


}

