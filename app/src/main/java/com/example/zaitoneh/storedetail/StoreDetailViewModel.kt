package com.example.zaitoneh.storedetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.R
import com.example.zaitoneh.database.StoreDatabaseDao
import com.example.zaitoneh.database.Store
import kotlinx.android.synthetic.main.fragment_store_detail.view.*
import kotlinx.coroutines.*
import java.lang.Exception



class StoreDetailViewModel(
    private val storeKey: Long = 0L,
    dataSource: StoreDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its StoreDatabaseDao.
     */
    val database = dataSource
  


    //= MutableLiveData<Store?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val store = MediatorLiveData<Store>()


    private val _saveStoreToDataBase = MutableLiveData<Boolean?>()
    val saveStoreToDataBase: LiveData<Boolean?>
        get() = _saveStoreToDataBase


    private val _deleteStoreFromDataBase = MutableLiveData<Boolean?>()
    val deleteStoreFromDataBase: LiveData<Boolean?>
        get() = _deleteStoreFromDataBase



    private val _updateStoreToDataBase = MutableLiveData<Boolean?>()
    val updateStoreToDataBase: LiveData<Boolean?>
        get() = _updateStoreToDataBase




    private val _storeValidation = MutableLiveData<Boolean?>()
    val storeValidation: LiveData<Boolean?>
        get() = _storeValidation

       fun getStore() = store



    init {
        store.addSource(database.getStoreWithId(storeKey), store::setValue)
      //  _storeValidation.value=false
    }


    /**
     * Variable that tells the fragment whether it should navigate to [StoreTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToStoreTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [StoreTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToStoreTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("storedetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [StoreTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToStoreTracker.value = null
    }

    fun onClose() {
        _navigateToStoreTracker.value = true
    }

    private suspend fun insert(store: Store) {
        withContext(Dispatchers.IO) {
            database.insert(store)

        }
    }
    private suspend fun update(store: Store) {
        withContext(Dispatchers.IO) {
            database.update(store)
            Log.i("update",store.storeId.toString())
        }
    }


     fun   vaildateStore(newStore: Store) : Boolean {
         return !(newStore.storeName == "" || newStore.storeAddress == ""  || newStore.storeCode == "" )
         Log.i("vaildateStore", " inside vaildateStore")
            }


     fun onCreateStore(newStore: Store) {
         Log.i("vaildateStore", " onCreateStore")
         Log.i("viewModelstore",newStore.storeName)

             newStore.storeId= this.storeKey

            if (vaildateStore(newStore)) {
                Log.i("vaildateStore", " if  onCreateStore")
                uiScope.launch {
                    try {

                        if (storeKey==0L) {
                            insert(newStore)
                            _saveStoreToDataBase.value=true
                        }else{
                            Log.i("update","update")
                            update(newStore)
                            _updateStoreToDataBase.value=true

                        }

                    }
                    catch (E:Exception){
                        Log.i("Exception", "There is a problem in insert same store added")
                        if(newStore.storeId==0L)
                           _saveStoreToDataBase.value = false
                        else
                        _updateStoreToDataBase.value = false
                    }
                }
            }
            else{
                _storeValidation.value=false
            }
    }

fun OnDeleteStore() {

    uiScope.launch {
        try {
            Log.i("delete",store.value!!.storeId.toString())
            store!!.value?.storeId?.let { deleteStore(it) }
            _deleteStoreFromDataBase.value = true

        } catch (E: Exception) {
            Log.i("Exception", "The store was not deleted")
            _deleteStoreFromDataBase.value = false

        }
    }
}

    private suspend fun deleteStore(storeId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(storeId)

        }
    }


 fun   setSaveStoreToDataBase(){
     _saveStoreToDataBase.value=true
 }


    fun   setupdateStoreToDataBase(){
        _updateStoreToDataBase.value=true
    }



}

