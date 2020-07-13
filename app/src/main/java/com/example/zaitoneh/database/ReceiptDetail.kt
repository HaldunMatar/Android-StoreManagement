package com.example.zaitoneh.database



import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import java.util.*

//@Entity(tableName="receipts_table")
@Entity(tableName="receipt_table" , primaryKeys = arrayOf("item_id", "receipt_id"))

data class ReceiptDetail(

    @ColumnInfo(name = "item_id")
    var itemId: Long ,

    @ColumnInfo(name = "receipt_id")
    var receiptId: Long ,
    @ColumnInfo(name = "item_price")
    var itemPrice: Float ,

    @ColumnInfo(name = "amount")
    var amount : Float

)





