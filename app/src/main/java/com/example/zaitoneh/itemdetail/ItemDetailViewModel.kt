package com.example.zaitoneh.itemdetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaitoneh.database.ItemDatabaseDao
import com.example.zaitoneh.database.Item
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class ItemDetailViewModel(
    private val itemKey: Long = 0L,
    dataSource: ItemDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its ItemDatabaseDao.
     */
    val database = dataSource

    lateinit var  listitems : List<Item>

    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val item = MediatorLiveData<Item>()


   val items = database.getItemWithId(111)

    private val _saveItemToDataBase = MutableLiveData<Boolean?>()
    val saveItemToDataBase: LiveData<Boolean?>
        get() = _saveItemToDataBase

    private val _itemValidation = MutableLiveData<Boolean?>()
    val itemValidation: LiveData<Boolean?>
        get() = _itemValidation

       fun getItem() = item

    init {
        _itemValidation.value=false
        _saveItemToDataBase.value=true
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
        viewModelJob.cancel()
    }





    var tempItem :Item = Item()

    fun getItemByIdForDisplay(item_id: Long) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.

            withContext(Dispatchers.IO) {
                val item = database.get(item_id) ?: return@withContext
                tempItem=item
            }

            // Setting this state variable to true will alert the observer and trigger navigation.

        }

    }



    private suspend fun getItemById(item_id: Long) {
        withContext(Dispatchers.IO) {

            val item = database.get(item_id) ?: return@withContext

        }
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


     fun   vaildateItem(newItem: Item) : Boolean {
         return !(newItem.itemLevel1 == "" || newItem.itemLevel2 == ""  || newItem.itemLevel3 == "" )

            }


        fun onCreateItem(newItem: Item,radioGroup: RadioGroup) {
            val radioButton =  radioGroup.findViewById<RadioButton>( radioGroup.checkedRadioButtonId)
            val   tempItem=newItem
            tempItem.itemMain= radioButton.id.toString()
            Log.i("viewModelitem","create new item")
            if (vaildateItem(tempItem)) {
                uiScope.launch {
                    try {
                        insert(tempItem)
                        _saveItemToDataBase.value=true
                    }
                    catch (E:Exception){
                        Log.i("Exception", "There is a problem in insert same item added")
                        _saveItemToDataBase.value = false
                    }
                }
            }
            else{
                _itemValidation.value=false
            }
    }


 fun   setSaveItemToDataBase(){
     _saveItemToDataBase.value=true
 }




}

