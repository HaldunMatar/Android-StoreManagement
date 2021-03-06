package com.example.zaitoneh.database



import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import java.util.*

//@Entity(tableName="receipts_table")
@Entity(tableName="receipt_detail_table" , primaryKeys = arrayOf("item_id", "receipt_id"))

data class ReceiptDetail(

    @ColumnInfo(name = "item_id")
    var itemId: Long=0 ,

    @ColumnInfo(name = "receipt_id")
    var receiptId: Long=0 ,
    @ColumnInfo(name = "item_price")
    var itemPrice: Float =10F,

    @ColumnInfo(name = "amount")
    var amount : Float=0F

)





