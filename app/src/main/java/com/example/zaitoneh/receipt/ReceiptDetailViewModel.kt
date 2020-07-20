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
import java.lang.Error
import java.lang.Exception



class ReceiptDetailViewModel(
    private val receiptKey: Long = 0L,
    dataSource: ReceiptDatabaseDao) : ViewModel() {
    val database = dataSource
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

    fun onCreateReceipt(newReceipt: Receipt) {
                uiScope.launch {
                    try {
                        if (receiptKey == 0L) {
                            insert(newReceipt)
                            Log.i("Insert: ","Done!")
                            _saveReceiptToDataBase.value = true
                        }
                    }
                    catch (E:Exception){
                        Log.i("Insert: ","Error")

                    }
                }
    }
    private suspend fun insert(receipt: Receipt) {
        withContext(Dispatchers.IO) {
            database.insert(receipt)
        }
    }


 fun   setSaveReceiptToDataBase(){
     _saveReceiptToDataBase.value=true
 }





}

