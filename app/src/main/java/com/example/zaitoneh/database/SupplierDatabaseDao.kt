package com.example.zaitoneh.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SupplierDatabaseDao{
    @Insert
    fun insert(supplier:Supplier)

    @Update
    fun update(supplier:Supplier)

    @Query("DELETE FROM supplier_table WHERE supId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of supplier that matches the supplied key
     */
    @Query("SELECT * from supplier_table WHERE supId = :key")
    fun get(key: Long): Supplier?


    @Query("DELETE FROM supplier_table")
    fun clear()

    @Query("SELECT * FROM supplier_table ORDER BY supId DESC")
    fun getAllSuppliers(): LiveData<List<Supplier>>

    /**
     * Selects and returns the supplier with given supplier Id.
     */
    @Query("SELECT * from supplier_table WHERE supId = :key")
    fun getSupplierWithId(key: Long): LiveData<Supplier>


    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM supplier_table ORDER BY supId DESC LIMIT 1")
    fun getlatestSupplier(): Supplier?
    @Query("SELECT * FROM supplier_table ORDER BY supId DESC")
    fun getSuppliers(): List<Supplier>
}