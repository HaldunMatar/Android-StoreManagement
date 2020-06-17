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

    fun getItem() = item

    init {
        _saveItemToDataBase.value=false
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

  /*  private suspend fun getAll1():List<Item> {
        Log.i("viewModelitem","getAll1")
        return withContext(Dispatchers.IO) {
             database.getAllItems()
        }
    }

      fun getAll2(){
        Log.i("viewModelitem","getAll2")
          uiScope.launch {
              listitems= getAll1()
        }
    }*/
    /* fun   vaildateItem(newItem: Item,radioGroup: RadioGroup) : Int {
        val radioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        if (radioButton == null) {
            return 2
        } else {
            if (newItem.itemLevel1 == null || newItem.itemLevel2 == null || newItem.itemLevel3 == null || newItem.itemLevel3 == null) {
                return 3
            } else {
                return 1
            }

        }
    }*/


        fun onCreateItem(newItem: Item,radioGroup: RadioGroup) {
            val radioButton =  radioGroup.findViewById<RadioButton>( radioGroup.checkedRadioButtonId)
          val   tempItem=newItem
            tempItem.itemMain= radioButton.text.toString()
        Log.i("viewModelitem","create new item")
        uiScope.launch {
            insert(tempItem)
            _saveItemToDataBase.value=true
            Log.i("viewModelitem",tempItem.toString())
        }
    }


 fun   setSaveItemToDataBase(){
     _saveItemToDataBase.value=false
 }




}

