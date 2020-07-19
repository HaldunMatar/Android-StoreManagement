package com.example.zaitoneh.receipt

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.database.ReceiptDatabaseDao
import com.example.zaitoneh.database.Receipt
import kotlinx.coroutines.*
import java.lang.Exception



class ReceiptDetailViewModel(
    private val receiptKey: Long = 0L,
    dataSource: ReceiptDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its ReceiptDatabaseDao.
     */
    val database = dataSource
  


    //= MutableLiveData<Receipt?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receipt = MediatorLiveData<Receipt>()


    private val _saveReceiptToDataBase = MutableLiveData<Boolean?>()
    val saveReceiptToDataBase: LiveData<Boolean?>
        get() = _saveReceiptToDataBase





    init {
        receipt.addSource(database.getReceiptWithId(receiptKey), receipt::setValue)
      //  _receiptValidation.value=false
    }



    override fun onCleared() {
        super.onCleared()
        Log.i("receiptdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [ReceiptTrackerFragment]
     */
    

    private suspend fun insert(receipt: Receipt) {
        withContext(Dispatchers.IO) {
            database.insert(receipt)
        }
    }

    // fun onCreateReceipt(newReceipt: Receipt,empSpinner: Spinner) {
    fun onCreateReceipt(newReceipt: Receipt) {
      //  val employee:Employee =empSpinner.selectedItem as Employee
        // newReceipt.receiptEmpId=employee.employeeId
         Log.i("vaildateEmployeeID: ", newReceipt.receiptEmpId.toString())
        Log.i("vaildateEmployeeID: ", newReceipt.receiptDate.toString())
        Log.i("vaildateEmployeeID: ", newReceipt.receiptId.toString())
        Log.i("vaildateEmployeeID: ", newReceipt.receiptStoreId.toString())
        Log.i("vaildateEmployeeID: ", newReceipt.receiptSupId.toString())
        Log.i("vaildateEmployeeID: ", newReceipt.receiptNote.toString())

         //Log.i("viewModelreceipt",newReceipt.receiptDepId.toString())
             newReceipt.receiptId= this.receiptKey
                uiScope.launch {
                        if (receiptKey==0L) {
                            insert(newReceipt)
                            _saveReceiptToDataBase.value=true
                    }
                }
         
         
    }




 fun   setSaveReceiptToDataBase(){
     _saveReceiptToDataBase.value=true
 }





}

