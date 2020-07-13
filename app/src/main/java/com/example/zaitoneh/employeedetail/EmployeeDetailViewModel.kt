package com.example.zaitoneh.employeedetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.R
import com.example.zaitoneh.database.EmployeeDatabaseDao
import com.example.zaitoneh.database.Employee
import kotlinx.android.synthetic.main.fragment_employee_detail.view.*
import kotlinx.coroutines.*
import java.lang.Exception



class EmployeeDetailViewModel(
    private val employeeKey: Long = 0L,
    dataSource: EmployeeDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its EmployeeDatabaseDao.
     */
    val database = dataSource
  


    //= MutableLiveData<Employee?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val employee = MediatorLiveData<Employee>()

     var  employee1 = Employee()
    private val _saveEmployeeToDataBase = MutableLiveData<Boolean?>()
    val saveEmployeeToDataBase: LiveData<Boolean?>
        get() = _saveEmployeeToDataBase


    private val _deleteEmployeeFromDataBase = MutableLiveData<Boolean?>()
    val deleteEmployeeFromDataBase: LiveData<Boolean?>
        get() = _deleteEmployeeFromDataBase



    private val _updateEmployeeToDataBase = MutableLiveData<Boolean?>()
    val updateEmployeeToDataBase: LiveData<Boolean?>
        get() = _updateEmployeeToDataBase




    private val _employeeValidation = MutableLiveData<Boolean?>()
    val employeeValidation: LiveData<Boolean?>
        get() = _employeeValidation

       fun getEmployee() = employee



    init {
        employee.addSource(database.getEmployeeWithId(employeeKey), employee::setValue)
      //  _employeeValidation.value=false
    }


    /**
     * Variable that tells the fragment whether it should navigate to [EmployeeTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToEmployeeTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [EmployeeTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToEmployeeTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("employeedetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [EmployeeTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToEmployeeTracker.value = null
    }

    fun onClose() {
        _navigateToEmployeeTracker.value = true
    }

    private suspend fun insert(employee: Employee) {
        withContext(Dispatchers.IO) {
            database.insert(employee)

        }
    }
    private suspend fun update(employee: Employee) {
        withContext(Dispatchers.IO) {
            database.update(employee)
            Log.i("update",employee.employeeId.toString())
        }
    }


     fun   vaildateEmployee(newEmployee: Employee) : Boolean {
         return !false
         Log.i("vaildateEmployee", " inside vaildateEmployee")
            }


     fun onCreateEmployee(newEmployee: Employee) {
         Log.i("vaildateEmployee", " onCreateEmployee")
         Log.i("viewModelemployee",newEmployee.toString())

             newEmployee.employeeId= this.employeeKey

            if (vaildateEmployee(newEmployee)) {
                Log.i("vaildateEmployee", " if  onCreateEmployee")
                uiScope.launch {
                    try {

                        if (employeeKey==0L) {
                            insert(newEmployee)
                            _saveEmployeeToDataBase.value=true
                        }else{
                            Log.i("update","update")
                            update(newEmployee)
                            _updateEmployeeToDataBase.value=true

                        }

                    }
                    catch (E:Exception){
                        Log.i("Exception", "There is a problem in insert same employee added")
                        if(newEmployee.employeeId==0L)
                           _saveEmployeeToDataBase.value = false
                        else
                        _updateEmployeeToDataBase.value = false
                    }
                }
            }
            else{
                _employeeValidation.value=false
            }
    }

fun OnDeleteEmployee() {

    uiScope.launch {

        try {
            Log.i("delete",employee.value!!.employeeId.toString())
            employee!!.value?.employeeId?.let { deleteEmployee(it) }
            _deleteEmployeeFromDataBase.value = true

        } catch (E: Exception) {
            Log.i("Exception", "The employee was not deleted")
            _deleteEmployeeFromDataBase.value = false

        }
    }
}

    private suspend fun deleteEmployee(employeeId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(employeeId)

        }
    }


 fun   setSaveEmployeeToDataBase(){
     _saveEmployeeToDataBase.value=true

 }


    fun   setupdateEmployeeToDataBase(){
        _updateEmployeeToDataBase.value=true
    }
    fun  getEmployee(id:Long) {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                getEmployeeFromDB(id)
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(1) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getEmployeeFromDB(id:Long): Employee {
        // simulate long running task
        var employee= database.get(id)!!
        this.employee1=employee;
        return employee
    }


}

