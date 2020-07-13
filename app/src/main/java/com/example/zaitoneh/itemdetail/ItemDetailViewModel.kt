package com.example.zaitoneh.itemdetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.R
import com.example.zaitoneh.database.ItemDatabaseDao
import com.example.zaitoneh.database.Item
import kotlinx.android.synthetic.main.fragment_item_detail.view.*
import kotlinx.coroutines.*
import java.lang.Exception



class ItemDetailViewModel(
    private val itemKey: Long = 0L,
    dataSource: ItemDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its ItemDatabaseDao.
     */
    val database = dataSource
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    lateinit var  listitems : List<Item>

    //= MutableLiveData<Item?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val item = MediatorLiveData<Item>()


    private val _saveItemToDataBase = MutableLiveData<Boolean?>()
    val saveItemToDataBase: LiveData<Boolean?>
        get() = _saveItemToDataBase


    private val _deleteItemFromDataBase = MutableLiveData<Boolean?>()
    val deleteItemFromDataBase: LiveData<Boolean?>
        get() = _deleteItemFromDataBase



    private val _updateItemToDataBase = MutableLiveData<Boolean?>()
    val updateItemToDataBase: LiveData<Boolean?>
        get() = _updateItemToDataBase




    private val _itemValidation = MutableLiveData<Boolean?>()
    val itemValidation: LiveData<Boolean?>
        get() = _itemValidation

       fun getItem() = item



    init {
        item.addSource(database.getItemWithId(itemKey), item::setValue)
    }


    /**
     * Variable that tells the fragment whether it should navigate to [ItemTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToItemTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [ItemTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToItemTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("itemdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [ItemTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToItemTracker.value = null
    }

    fun onClose() {
        _navigateToItemTracker.value = true
    }

    private suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            database.insert(item)

        }
    }
    private suspend fun update(item: Item) {
        withContext(Dispatchers.IO) {
            database.update(item)
            Log.i("update",item.itemId.toString())
        }
    }


     fun   vaildateItem(newItem: Item) : Boolean {
         return !(newItem.itemLevel1 == "" || newItem.itemLevel2 == ""  || newItem.itemLevel3 == "" )

            }


     fun onCreateItem(newItem: Item,radioGroup: RadioGroup) {

          val radioButton =  radioGroup.findViewById<RadioButton>( radioGroup.checkedRadioButtonId)
            val   tempItem=newItem
                 tempItem.itemId= itemKey


               when(radioButton.id){
                   R.id.itemMain_kratin_radio-> tempItem.itemMain="Kratin"
                   R.id.itemMain_box_radio-> tempItem.itemMain="Box"
                   R.id.itemMain_materials_radio-> tempItem.itemMain="Materials"
               }

            Log.i("viewModelitem",tempItem.itemMain)
            if (vaildateItem(tempItem)) {
                uiScope.launch {
                    try {

                        if (itemKey==0L) {
                            insert(tempItem)
                            _saveItemToDataBase.value=true
                        }else{
                            Log.i("update","update")
                            update(tempItem)
                            _updateItemToDataBase.value=true

                        }

                    }
                    catch (E:Exception){
                        Log.i("Exception", "There is a problem in insert same item added")
                        _saveItemToDataBase.value = false
                        _updateItemToDataBase.value = false
                    }
                }
            }
            else{
                _itemValidation.value=false
            }
    }

fun OnDeleteItem() {

    uiScope.launch {
        try {
            Log.i("delete",item.value!!.itemId.toString())
            item!!.value?.itemId?.let { deleteItem(it) }
            _deleteItemFromDataBase.value = true

        } catch (E: Exception) {
            Log.i("Exception", "The item was not deleted")
            _deleteItemFromDataBase.value = false

        }
    }
}

    private suspend fun deleteItem(itemId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(itemId)

        }
    }


 fun   setSaveItemToDataBase(){
     _saveItemToDataBase.value=true
 }


    fun   setupdateItemToDataBase(){
        _updateItemToDataBase.value=true
    }

/*
   fun main() {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                calculateSum()
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(1) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun calculateSum(): Item {
         // simulate long running task
        item1= database.get(1)!!
        return item1
    }

 */

}

