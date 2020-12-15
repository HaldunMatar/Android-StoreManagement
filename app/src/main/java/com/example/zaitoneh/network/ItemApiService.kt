/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.example.zaitoneh.database.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

//private const val BASE_URL ="http://localhost:8080/"

private const val BASE_URL ="http://10.0.2.2:8080/"

//private const val BASE_URL = "https://murmuring-forest-07493.herokuapp.com/"

enum class ItemApiFilter(val value: String) { SHOW_RENT("id"), SHOW_BUY("level1"), SHOW_ALL("all") }

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface ItemApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [MarsProperty] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */




    @GET("ItemId")
    fun getItemById( @Query("ItemId") ItemId: Long):Deferred<Item>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result

    @GET("listItemsREST")
    fun getItems():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Item>>

    @POST("newItem")
    fun newItem(@Body newDestination: Item):Deferred<Util>

    @POST("updateItem")
    fun updateItem(@Body newDestination: Item):Deferred<Util>

    @DELETE("deleteItem")
        fun deleteItem( @Query("itemId") ItemId: Long):Deferred<Boolean>


//***********************************************

    @GET("StoreId")
    fun getStoreById( @Query("storeId") storeId: Long):Deferred<Store>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result



    @POST("newStore")
    fun newStore(@Body newDestination: Store):Deferred<Util>

    @POST("updateStore")
    fun updateStore(@Body newDestination: Store):Deferred<Util>

    @DELETE("deleteStore")
    fun deleteStore( @Query("storeId") storeId: Long):Deferred<Boolean>


    @GET("listStoresREST")
    fun getStores():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Store>>

//***********************************************

    @GET("EmployeeId")
    fun getEmployeeById( @Query("employeeId") employeeId: Long?):Deferred<Employee?>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result



    @POST("newEmployee")
    fun newEmployee(@Body newDestination: Employee):Deferred<Util>

    @POST("updateEmployee")
    fun updateEmployee(@Body newDestination: Employee):Deferred<Util>

    @DELETE("deleteEmployee")
    fun deleteEmployee( @Query("employeeId") employeeId: Long):Deferred<Boolean>


    @GET("listEmployeeREST")
    fun getEmployees():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Employee>>

    //***********************************************

    @GET("SupplierId")
    fun getSupplierById( @Query("supplierId") supplierId: Long):Deferred<Supplier>
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result



    @POST("newSupplier")
    fun newSupplier(@Body newDestination: Supplier):Deferred<Util>

    @POST("updateSupplier")
    fun updateSupplier(@Body newDestination: Supplier):Deferred<Util>

    @DELETE("deleteSupplier")
    fun deleteSupplier( @Query("supplierId") supplierId: Long):Deferred<Boolean>


    @GET("listSupplierREST")
    fun getSuppliers():
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Supplier>>



    //***********************************************

    @GET("departmentId")
    fun getDepartmentById( @Query("departmentId") departmentId: Long):Deferred<Department>
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result



    @POST("newDepartment")
    fun newDepartment(@Body newDestination: Department):Deferred<Util>

    @POST("updateDepartment")
    fun updateDepartment(@Body newDestination: Department):Deferred<Util>

    @DELETE("deleteDepartment")
    fun deleteDepartment( @Query("departmentId") departmentId: Long):Deferred<Boolean>


    @GET("listDepartmentREST")
    fun getDepartments():
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Department>>

    //***********************************************************

    @GET("receiptId")
    fun getReceiptById( @Query("receiptId") receiptId: Long):Deferred<Receipt>
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result



    @POST("newReceipt")
    fun newReceipt(@Body newDestination: Receipt):Deferred<Util>

    @POST("updateReceipt")
    fun updateReceipt(@Body newDestination: Receipt):Deferred<Util>

    @DELETE("deleteReceipt")
    fun deleteReceipt( @Query("receiptId") receiptId: Long):Deferred<Boolean>


    @GET("listReceiptREST")
    fun getReceipts():
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Receipt>>


//***********************************************************
@POST("newReceiptDetail")
fun newReceiptDetail(@Body receiptDetail: ReceiptDetail):Deferred<Util>

    @GET("listReceiptDetailREST")
    fun getReceiptDetails(@Query("receiptId") receiptId: Long):
// The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<ReceiptDetail>>


}




/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object StoreApi {
    val retrofitService : ItemApiService by lazy { retrofit.create(ItemApiService::class.java) }
}
