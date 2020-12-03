package com.example.zaitoneh.receipt

import android.os.Build
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.ItemApiFilter
import com.example.android.marsrealestate.network.StoreApi
import com.example.zaitoneh.R
import com.example.zaitoneh.database.*
import com.example.zaitoneh.employeetracker.EmployeeTrackerViewModel
import com.example.zaitoneh.itemtracker.ItemApiStatus
import kotlinx.coroutines.*
import java.lang.Error
import java.lang.Exception
import java.time.LocalDate
import java.util.*


class ReceiptDetailViewModel(
    private val receiptKey: Long = 0L,
    dataSource: ReceiptDatabaseDao,receiptDetailDataSource:ReceiptDetailDatabaseDao) : ViewModel() {
     var  latestreciept:Long=0L
    val database = dataSource
    var receiptEdite = Receipt()
    val receiptdetailDatabase=receiptDetailDataSource
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var receipt = MediatorLiveData<Receipt>()
     var receiptTest = MutableLiveData<Receipt>()
    fun getReceipt() = receipt
    private val _saveReceiptToDataBase = MutableLiveData<Boolean?>()
    val saveReceiptToDataBase: LiveData<Boolean?>
        get() = _saveReceiptToDataBase


    private val _navigateToEditReceipt = MutableLiveData<Long>()
    val  navigateToEditReceipt: LiveData<Long?>
        get() = _navigateToEditReceipt


     var receiptdetails   : LiveData<List<ReceiptDetail>> = receiptDetailDataSource.getAllReceiptDetails()
    var selectedReceipt = MutableLiveData<Receipt?>()






     var employeesList = MediatorLiveData<List<Employee>>()


    init {
        uiScope.launch {
            if (receiptKey != 0L) {
                var selectedReceipt = MutableLiveData<Receipt?>()
                selectedReceipt.value=    getReceiptFromNet(receiptKey)
                receipt.addSource(selectedReceipt, receipt::setValue)
            }
             getEmployeesNet(ItemApiFilter.SHOW_ALL)
        }


    }
    fun getEmployeesNet(filter: ItemApiFilter) {
        uiScope.launch {
            var getPropertiesDeferred = StoreApi.retrofitService.getEmployees()
            try {
                val listResult = getPropertiesDeferred.await()
                employeesList.value = listResult
            } catch (e: Exception) {
                employeesList.value = ArrayList()
            }
        }
    }



    private suspend fun getReceiptFromNet(receiptId: Long): Receipt? {
        return withContext(Dispatchers.IO) {
            var itemDeferred = StoreApi.retrofitService.getReceiptById(receiptId)
            itemDeferred.await()
                  }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("receiptdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }
    fun fromDate(date: Date):Long{
        return date.time;
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onCreateReceipt(newReceipt: Receipt) {
                uiScope.launch {
                    try {
                        if (receiptKey == 0L) 
                        {
                            newReceipt.receiptDate= fromDate(Date())
                            insert(newReceipt)
                            Log.i("Insert: ","1- Done! Reciept Master")
                            _saveReceiptToDataBase.value = true
                        }
                    }
                    catch (E:Exception){
                      Log.i("Insert: ","Error Reciept Master!!!")
                    }
                }

    }
    private suspend fun insert(receipt: Receipt) {
        withContext(Dispatchers.IO) {
            database.insert(receipt)
        }
    }

    fun getLatestReciept() {

        GlobalScope.launch {
            val result = async {
                 latestreciept=getLatestRecieptDB().receiptId

                Log.i("getLatestRecieptDB",latestreciept.toString())
            }
        }
        runBlocking {
            delay(50) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getLatestRecieptDB(): Receipt {

        return database.getlatestReceipt() as Receipt
    }
 fun   setSaveReceiptToDataBase(){
     _saveReceiptToDataBase.value=true
 }
    fun onReceiptDetailClicked(receiptId: Long) {
        Log.i("onclick","onReceiptClicked")
        _navigateToEditReceipt.value=receiptId
    }


    fun getReceipt(id: Long) {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                getReceiptFromDB(id)
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(200) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getReceiptFromDB(id: Long): Receipt {
        // simulate long running task
      //  var receiptEdite = database.get(id)!!
      //  var itemDeferred = StoreApi.retrofitService.getReceiptById(id)
    //    Log.i("getReceiptFromNet", " getReceiptFromNet getReceiptFromDB  =   after  + "  + itemDeferred.await().toString() );
        this.receiptEdite = receiptEdite;
     return receiptEdite
      //  return  itemDeferred.await()
    }
//******************************************

    private suspend fun insertNet(receipt: Receipt) {
        withContext(Dispatchers.IO) {
            var newItemDeferred = StoreApi.retrofitService.newReceipt(receipt)
        }
    }
    //----------------------
    fun onCreateReceiptNet(NewReceipt: Receipt) {

        Log.i("onCreateReceiptNet",NewReceipt.toString())

        if (vaildateReceipt(NewReceipt)) {
            Log.i("vaildateEmployee", " if  onCreateEmployee")
            uiScope.launch {
                try {

                    //if (receiptKey.equals("null")) {
                    if (receiptKey==0L) {
                        Log.i("insertNet", " insertNet out")
                        insertNet(NewReceipt)
                        _saveReceiptToDataBase.value=true
                    }else{
                        NewReceipt.receiptId= receiptKey?.toLong()!!
                        Log.i("update","update")
                       // updateNet(NewReceipt)
                       // _updateReceiptToDataBase.value=true

                    }

                }
                catch (E:Exception){
                    Log.i("Exception", "There is a problem in insert ")
                  /*  if(NewReceipt.receiptId==0L)
                     //   _saveEmployeeToDataBase.value = false
                    else
                       // _updateEmployeeToDataBase.value = false*/
                }
            }
        }
        else{
          //  _employeeValidation.value=false
        }
    }

    fun vaildateReceipt(receipt: Receipt): Boolean {
        return !false
        Log.i("vaildateReceipt", " inside vaildateReceipt")
    }

}

