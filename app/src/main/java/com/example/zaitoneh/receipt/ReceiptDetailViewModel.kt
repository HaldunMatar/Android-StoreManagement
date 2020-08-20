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
import com.example.zaitoneh.R
import com.example.zaitoneh.database.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Error
import java.lang.Exception
import java.time.LocalDate
import java.util.*


class ReceiptDetailViewModel(
    private val receiptKey: Long = 0L,
    dataSource: ReceiptDatabaseDao,receiptDetailDataSource:ReceiptDetailDatabaseDao) : ViewModel() {
     var  latestreciept: Long=0L
    val database = dataSource
    val receiptdetailDatabase=receiptDetailDataSource
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receipt = MediatorLiveData<Receipt>()
    private val _saveReceiptToDataBase = MutableLiveData<Boolean?>()
    val saveReceiptToDataBase: LiveData<Boolean?>
        get() = _saveReceiptToDataBase

     var receiptdetails   : LiveData<List<ReceiptDetail>> = receiptdetailDatabase.getAllReceiptDetails(54)


     fun updateList1() {

         receiptdetails   = receiptdetailDatabase.getAllReceiptDetails(latestreciept)
    }


    init {
        receipt.addSource(database.getReceiptWithId(receiptKey), receipt::setValue)
      //  _receiptValidation.value=false
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
                        if (receiptKey == 0L) {
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
                setLatestRecieptOnMainThread(latestreciept)
            }
        }
        runBlocking {
            delay(0) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getLatestRecieptDB(): Receipt {

        return database.getlatestReceipt() as Receipt
    }

    private suspend fun setLatestRecieptOnMainThread(input:Long){

        withContext(Main){
            setNewText(input)
        }
    }

    private fun setNewText(input:Long){

         latestreciept=input
Log.i("insert" ," inside setNew = "+latestreciept)
        receiptdetails = receiptdetailDatabase.getAllReceiptDetails(latestreciept)
    }


    fun   setSaveReceiptToDataBase(){
     _saveReceiptToDataBase.value=true
 }
    fun onReceiptDetailClicked(receiptId: Long) {
        Log.i("onclick","onReceiptClicked")
        //_navigateToEditReceipt.value=receiptId
    }


}

