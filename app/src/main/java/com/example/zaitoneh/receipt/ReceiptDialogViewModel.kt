package com.example.zaitoneh.receipt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.ItemApiFilter
import com.example.android.marsrealestate.network.StoreApi
import com.example.zaitoneh.database.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*


class ReceiptDialogViewModel(


) : ViewModel() {
  //  val database = dataSource
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val receiptdetail = MediatorLiveData<ReceiptDetail>()
    private val _saveReceiptDetailToDataBase = MutableLiveData<Boolean?>()
    val saveReceiptToDataBase: LiveData<Boolean?>
        get() = _saveReceiptDetailToDataBase

    var itemList = MediatorLiveData<List<Item>>()


    init {
        uiScope.launch {

            getItemsNet(ItemApiFilter.SHOW_ALL)
        }

        //  _receiptValidation.value=false
    }
    fun getItemsNet(filter: ItemApiFilter) {
        uiScope.launch {
            var getitem = StoreApi.retrofitService.getItems()
            try {
                itemList.value=getitem.await()
            } catch (e: Exception) {
                itemList.value = ArrayList()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        Log.i("receiptdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }
//******************************************

    private suspend fun insertNet(receiptDetail: ReceiptDetail) {
        withContext(Dispatchers.IO) {
            Log.i("insertNet", receiptDetail.toString())
            var newItemDeferred = StoreApi.retrofitService.newReceiptDetail(receiptDetail)
        }
    }
    fun onCreateReceiptDetailNet(newReceiptDetail: ReceiptDetail) {

        Log.i("insertNet", newReceiptDetail.toString())
            uiScope.launch {
                try {
                        insertNet(newReceiptDetail)
                    
                   // getItemsNet(ItemApiFilter.SHOW_ALL)

                }
                catch (E:Exception){
                    Log.i("Exception", "There is a problem in insert ")

                }
            }
        }






  /*  fun onCreateReceiptDetail(newReceiptDetail: ReceiptDetail) {

        uiScope.launch {
            Log.i("insert before ","2-Insert done Detail Reciept!" +newReceiptDetail.toString())
           // insert(newReceiptDetail)

            Log.i("insert","  Done!  Detail Reciept Added successfully"+newReceiptDetail.toString() )
        }
    }*/
    private suspend fun insert(receiptdetail: ReceiptDetail) {
        withContext(Dispatchers.IO) {
            try {
               // database.insert(receiptdetail)


            }
            catch (e:Exception){
                Log.i("Insert: ","Already Added!")

            }
        }
    }

    fun   setSaveReceiptToDataBase(){
      //  _saveReceiptDetailToDataBase.value=true
    }





}

