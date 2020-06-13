package com.example.zaitoneh.database
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDatabaseDao{
    @Insert
    fun insert(item:Item)

    @Update
    fun update(item: Item)

    @Query("DELETE FROM items_table WHERE itemId = :key")
    fun delete(key: Long)
    /**
     * Selects and returns the row of item that matches the supplied key
     */
    @Query("SELECT * from items_table WHERE itemId = :key")
    fun get(key: Long): Item?


    @Query("DELETE FROM items_table")
    fun clear()

    @Query("SELECT * FROM items_table ORDER BY itemId DESC")
    fun getAllItems(): LiveData<List<Item>>

    /**
     * Selects and returns the item with given item Id.
     */
    @Query("SELECT * from items_table WHERE itemId = :key")
    fun getItemWithId(key: Long): LiveData<Item>

}