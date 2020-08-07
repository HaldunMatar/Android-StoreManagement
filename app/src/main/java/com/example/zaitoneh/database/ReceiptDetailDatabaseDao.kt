package com.example.zaitoneh.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReceiptDetailDatabaseDao{
    @Insert
    fun insert(receiptDetail:ReceiptDetail)

    @Update
    fun update(receiptDetail:ReceiptDetail)

    @Query(" DELETE FROM receipt_detail_table WHERE item_id = :item_key and  receipt_id = :receipt_key     ")
    fun delete(
        item_key: Long,
        receipt_key: Long
    )
    /**
     * Selects and returns the row of receiptDetail that matches the supplied key
     */
    @Query("SELECT * from receipt_detail_table WHERE  item_id = :item_key and  receipt_id = :receipt_key")
    fun get(  item_key: Long,
              receipt_key: Long): ReceiptDetail?


    @Query("DELETE FROM receipt_detail_table")
    fun clear()

    @Query("SELECT * FROM receipt_detail_table ORDER BY receipt_id,item_id DESC")
    fun getAllReceiptDetails(): LiveData<List<ReceiptDetail>>

    /**
     * Selects and returns the receiptDetail with given receiptDetail Id.
     */
    @Query("SELECT * from receipt_detail_table WHERE item_id = :item_key and  receipt_id = :receipt_key")
    fun getReceiptDetailWithId( item_key: Long,
                                receipt_key: Long): LiveData<ReceiptDetail>





    @Query("SELECT * FROM receipt_detail_table WHERE    receipt_id = :receipt_key")
    fun getReceiptDetails( receipt_key: Long): List<ReceiptDetail>

}