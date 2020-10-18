package com.example.zaitoneh.supplierdetail

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.StoreApi
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.database.SupplierDatabaseDao
import com.example.zaitoneh.database.Supplier
import kotlinx.android.synthetic.main.fragment_supplier_detail.view.*
import kotlinx.coroutines.*
import java.lang.Exception



class SupplierDetailViewModel(
    private val supplierKey: Long = 0L,
    dataSource: SupplierDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SupplierDatabaseDao.
     */
    val database = dataSource
    lateinit  var sups :List<Supplier>


    //= MutableLiveData<Supplier?>()
    /** Coroutine setup variables */
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    


    private val _saveSupplierToDataBase = MutableLiveData<Boolean?>()
    val saveSupplierToDataBase: LiveData<Boolean?>
        get() = _saveSupplierToDataBase


    private val _deleteSupplierFromDataBase = MutableLiveData<Boolean?>()
    val deleteSupplierFromDataBase: LiveData<Boolean?>
        get() = _deleteSupplierFromDataBase



    private val _updateSupplierToDataBase = MutableLiveData<Boolean?>()
    val updateSupplierToDataBase: LiveData<Boolean?>
        get() = _updateSupplierToDataBase




    private val _supplierValidation = MutableLiveData<Boolean?>()
    val supplierValidation: LiveData<Boolean?>
        get() = _supplierValidation

    private val supplier = MediatorLiveData<Supplier>()
    fun getSupplier() = supplier

   

    var selectedSupplier = MutableLiveData<Supplier?>()

    init {
        supplier.addSource(database.getSupplierWithId(supplierKey), supplier::setValue)
        //  _supplierValidation.value=false
        //supplier.addSource(database.getEmployeeWithId(supplierKey), supplier::setValue)
        Log.i("supplierInit","ReceiptDetailFragmentArgs=" + supplier.value.toString() )

        if(supplierKey!=0L) {
            initializeSupplierFromNet(supplierKey)
            supplier.addSource(selectedSupplier, supplier::setValue)

        }

    }

    private fun initializeSupplierFromNet(supplier: Long) {
        uiScope.launch {
            // Log.i("initializeEmployeeFromNet", "  brfore itemId   = " + EmployeeId );
            selectedSupplier.value = getSupplierFromNet(supplier)
            // Log.i("initializeEmployeeFromNet", " initializeEmployeeFromNet  = " + selectedEmployee.value);
        }

    }


    private suspend fun getSupplierFromNet(supplierId: Long): Supplier? {
        return withContext(Dispatchers.IO) {
            Log.i("getSupplierFromNet", " getSupplierFromNet  = " );
            var itemDeferred = StoreApi.retrofitService.getSupplierById(supplierId)
            Log.i("getSupplierFromNet", " getSupplierFromNet  =   after " );
            itemDeferred.await()

        }

    }


    private suspend fun insertNet(supplier: Supplier) {
        withContext(Dispatchers.IO) {
            Log.i("insertNet", " insertNet in ")
            val newDestination = supplier
            var newItemDeferred = StoreApi.retrofitService.newSupplier(newDestination)
        }
    }



    //----------------------
    fun onCreateSupplierNet(newSupplier: Supplier) {
        Log.i("vaildateSupplier", " onCreateSupplier")
        Log.i("viewModelsupplier",newSupplier.supFullName)

        newSupplier.supId= this.supplierKey

        if (vaildateSupplier(newSupplier)) {
            Log.i("vaildateSupplier", " if  onCreateSupplier")
            uiScope.launch {
                try {

                    if (supplierKey==0L) {
                        Log.i("insertNet", " insertNet out")
                        insertNet(newSupplier)
                        _saveSupplierToDataBase.value=true
                    }else{
                        Log.i("update","update")
                        updateNet(newSupplier)
                        _updateSupplierToDataBase.value=true

                    }

                }
                catch (E:Exception){
                    Log.i("Exception", "There is a problem in insert ")
                    if(newSupplier.supId==0L)
                        _saveSupplierToDataBase.value = false
                    else
                        _updateSupplierToDataBase.value = false
                }
            }
        }
        else{
            _supplierValidation.value=false
        }
    }

    private suspend fun updateNet(supplier: Supplier) {
        withContext(Dispatchers.IO) {
            val newDestination = supplier

            var updateItemDeferred = StoreApi.retrofitService.newSupplier(supplier)
        }
    }


    fun OnDeleteSupplierNet() {

        uiScope.launch {
            try {
                Log.i("delete",supplier.value!!.supId.toString())
                supplier!!.value?.supId?.let { deleteSupplierNet(it) }
                _deleteSupplierFromDataBase.value = true

            } catch (E: Exception) {
                Log.i("Exception", "The supplier was not deleted")
                _deleteSupplierFromDataBase.value = false

            }
        }
    }

    private suspend fun deleteSupplierNet(supplierId: Long) {

        withContext(Dispatchers.IO) {
            var itemDeferred = StoreApi.retrofitService.deleteSupplier(supplierId)
            if (!itemDeferred.await())
            {
                throw  Exception()
            }

        }
    }



//***********************



    /**
     * Variable that tells the fragment whether it should navigate to [SupplierTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSupplierTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SupplierTrackerFragment]
     */
    val navigateToSupplierTracker: LiveData<Boolean?>
        get() = _navigateToSupplierTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("supplierdetails" ," onCleared v M " )
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [SupplierTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSupplierTracker.value = null
    }

    fun onClose() {
        _navigateToSupplierTracker.value = true
    }

    private suspend fun insert(supplier: Supplier) {
        withContext(Dispatchers.IO) {
            database.insert(supplier)

        }
    }
    private suspend fun update(supplier: Supplier) {
        withContext(Dispatchers.IO) {
            database.update(supplier)

        }
    }


    fun   vaildateSupplier(newSupplier: Supplier) : Boolean {
        return !(newSupplier.supFullName == "" || newSupplier.supAddress == ""  || newSupplier.supMobile1 == "" )

    }


    fun onCreateSupplier(newSupplier: Supplier) {

        newSupplier.supId= this.supplierKey

        if (vaildateSupplier(newSupplier)) {
            uiScope.launch {
                try {

                    if (supplierKey==0L) {
                        insert(newSupplier)
                        _saveSupplierToDataBase.value=true
                    }else{
                        update(newSupplier)
                        _updateSupplierToDataBase.value=true
                    }

                }
                catch (E:Exception){
                    if(newSupplier.supId==0L)
                        _saveSupplierToDataBase.value = false
                    else
                        _updateSupplierToDataBase.value = false
                }
            }
        }
        else{
            _supplierValidation.value=false
        }
    }

    fun OnDeleteSupplier() {

        uiScope.launch {
            try {
                supplier!!.value?.supId?.let { deleteSupplier(it) }
                _deleteSupplierFromDataBase.value = true

            } catch (E: Exception) {
                _deleteSupplierFromDataBase.value = false

            }
        }
    }

    private suspend fun deleteSupplier(supplierId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(supplierId)
        }
    }


    fun   setSaveSupplierToDataBase(){
        _saveSupplierToDataBase.value=true
    }


    fun   setupdateSupplierToDataBase(){
        _updateSupplierToDataBase.value=true
    }

    fun getSuppliers() {
        println("Gone to calculate sum of a & b")

        GlobalScope.launch {
            val result = async {
                sups=  getSuppliersDB()
            }
            println("Sum of a & b is: ${result.await()}")
        }
        runBlocking {
            delay(200) // keeping jvm alive till calculateSum is finished
        }
    }

    suspend fun getSuppliersDB(): List<Supplier> {

        return database.getSuppliers()
    }

}

