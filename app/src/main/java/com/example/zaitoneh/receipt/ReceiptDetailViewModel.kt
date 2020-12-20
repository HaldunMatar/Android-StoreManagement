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
    var receiptKey: Long = 0L,
    dataSource: ReceiptDatabaseDao, receiptDetailDataSource:ReceiptDetailDatabaseDao) : ViewModel() {
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



    var selectedReceipt = MutableLiveData<Receipt?>()





    var receiptdetails = MediatorLiveData<List<ReceiptDetail>>()
     var employeesList = MediatorLiveData<List<Employee>>()
      var storList = MediatorLiveData<List<Store>>()
    var departmentList = MediatorLiveData<List<Department>>()
    var supplierList = MediatorLiveData<List<Supplier>>()


    init {
        uiScope.launch {
            if (receiptKey != 0L) {
                 selectedReceipt = MutableLiveData<Receipt?>()
                selectedReceipt.value=    getReceiptFromNet(receiptKey)
                receipt.addSource(selectedReceipt, receipt::setValue)

                getReceiptDetailsNet(receiptKey);
            }
             getEmployeesNet(ItemApiFilter.SHOW_ALL)


        }


    }

    fun getReceiptDetailsNet(id: Long) {
        uiScope.launch {
            var Deferred = StoreApi.retrofitService.getReceiptDetails(id)
            try {
                receiptdetails.value = Deferred.await()
                Log.i("getReceiptdetailsNet" , "  ge "+ receiptdetails.value!!.size.toString())
            } catch (e: Exception) {
             Log.i("getReceiptdetailsNet" , "erorr ")
            }
        }

    }
    fun getEmployeesNet(filter: ItemApiFilter) {
        uiScope.launch {
            var getPropertiesDeferred = StoreApi.retrofitService.getEmployees()
            var getstore = StoreApi.retrofitService.getStores()
            var getdep = StoreApi.retrofitService.getDepartments()
            var getsupp = StoreApi.retrofitService.getSuppliers()
            try {
                val listResult = getPropertiesDeferred.await()
                employeesList.value = listResult
                storList.value=getstore.await()
                departmentList.value=getdep.await()
                supplierList.value=getsupp.await()




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
    fun onReceiptClicked(receiptId: Long?) {
        Log.i("onclick","onReceiptClicked")
        _navigateToEditReceipt.value=receiptId
    }


    fun getReceipt(id: Long) {


        GlobalScope.launch {
            val result = async {
                getReceiptFromDB(id)
            }

        }
        runBlocking {
            delay(200) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getReceiptFromDB(id: Long): Receipt {

        this.receiptEdite = receiptEdite;
     return receiptEdite
      //  return  itemDeferred.await()
    }
//******************************************

    private suspend fun insertNet(receipt: Receipt): Long? {
        Log.i("navigateToEditReceipt", " receiptKey  receiptKey"+receiptKey.toString())
        return withContext(Dispatchers.IO) {
           var newItemDeferred = StoreApi.retrofitService.newReceipt(receipt)
          //  return@withContext 88L

            return@withContext newItemDeferred.await() as Long
        }


    }
    //----------------------
    fun onCreateReceiptNet(NewReceipt: Receipt) {

        Log.i("navigateToEditReceipt", " onCreateReceiptNet")

        if (vaildateReceipt(NewReceipt)) {

            uiScope.launch {
                try {


                    //if (receiptKey.equals("null")) {
                    if (receiptKey==0L) {

                       _navigateToEditReceipt.value=   insertNet(NewReceipt)
                     //   _navigateToEditReceipt.value= 88
                        _saveReceiptToDataBase.value=true


                    }else{
                        NewReceipt.receiptId= receiptKey?.toLong()!!

                        updateNet(NewReceipt)
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

    }
    private suspend fun updateNet(NewReceipt: Receipt) {
        withContext(Dispatchers.IO) {

            withContext(Dispatchers.IO) {
                var newItemDeferred = StoreApi.retrofitService.newReceipt(NewReceipt)
            }
        }
    }
}

