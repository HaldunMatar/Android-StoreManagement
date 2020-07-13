package com.example.zaitoneh.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReceiptDatabaseDao{
    @Insert
    fun insert(receipt:Receipt)

    @Update
    fun update(receipt:Receipt)

    @Query("DELETE FROM receipt_table WHERE receiptId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of receipt that matches the supplied key
     */
    @Query("SELECT * from receipt_table WHERE receiptId = :key")
    fun get(key: Long): Receipt?


    @Query("DELETE FROM receipt_table")
    fun clear()

    @Query("SELECT * FROM receipt_table ORDER BY receiptId DESC")
    fun getAllReceipts(): LiveData<List<Receipt>>

    /**
     * Selects and returns the receipt with given receipt Id.
     */
    @Query("SELECT * from receipt_table WHERE receiptId = :key")
    fun getReceiptWithId(key: Long): LiveData<Receipt>


    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM receipt_table ORDER BY receiptId DESC LIMIT 1")
    fun getlatestReceipt(): Receipt?

}