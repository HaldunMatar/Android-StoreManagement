package com.example.zaitoneh.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StoreDatabaseDao{
    @Insert
    fun insert(store:Store)

    @Update
    fun update(store:Store)

    @Query("DELETE FROM store_table WHERE storeId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of store that matches the supplied key
     */
    @Query("SELECT * from store_table WHERE storeId = :key")
    fun get(key: Long): Store?


    @Query("DELETE FROM store_table")
    fun clear()

    @Query("SELECT * FROM store_table ORDER BY storeId DESC")
    fun getAllStores(): LiveData<List<Store>>

    /**
     * Selects and returns the store with given store Id.
     */
    @Query("SELECT * from store_table WHERE storeId = :key")
    fun getStoreWithId(key: Long): LiveData<Store>


    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM store_table ORDER BY storeId DESC LIMIT 1")
    fun getlatestStore(): Store?

}