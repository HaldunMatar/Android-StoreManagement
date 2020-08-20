package com.example.zaitoneh.receipt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.database.ReceiptDetail
import com.example.zaitoneh.database.ReceiptDetailDatabaseDao
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*


class ReceiptDialogViewModel(

    dataSource: ReceiptDetailDatabaseDao
) : ViewModel() {
    val database = dataSource
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receiptdetail = MediatorLiveData<ReceiptDetail>()
    private val _saveReceiptDetailToDataBase = MutableLiveData<Boolean?>()
    val saveReceiptToDataBase: LiveData<Boolean?>
        get() = _saveReceiptDetailToDataBase
    init {

        //  _receiptValidation.value=false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("receiptdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    fun onCreateReceiptDetail(newReceiptDetail: ReceiptDetail) {

        uiScope.launch {
            Log.i("insert before ","2-Insert done Detail Reciept!" +newReceiptDetail.toString())
            insert(newReceiptDetail)

            Log.i("insert","  Done!  Detail Reciept Added successfully"+newReceiptDetail.toString() )
        }
    }
    private suspend fun insert(receiptdetail: ReceiptDetail) {
        withContext(Dispatchers.IO) {
            try {
                database.insert(receiptdetail)


            }
            catch (e:Exception){
                Log.i("Insert: ","Already Added!")

            }
        }
    }

    fun   setSaveReceiptToDataBase(){
        _saveReceiptDetailToDataBase.value=true
    }





}

