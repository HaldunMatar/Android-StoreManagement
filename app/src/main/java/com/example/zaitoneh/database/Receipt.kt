package com.example.zaitoneh.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import java.util.*

//@Entity(tableName="receipts_table")
@Entity(tableName="receipt_table")

data class Receipt(
    @PrimaryKey(autoGenerate = true)
    var receiptId: Long = 0L,

    @ColumnInfo(name = "receipt_note")
    var receiptNote: String = "",

    @ColumnInfo(name = "receipt_date")
    val receiptDate: Long = System.currentTimeMillis(),


    @ColumnInfo(name = "receipt_sup_id")
    var receiptSupId: Long ,

    @ColumnInfo(name = "receipt_city")
    var receiptCity: String = "",

    @ColumnInfo(name = "receipt_emp_id")
    var receiptEmpId: Long ,

    @ColumnInfo(name = "receipt_store_id")
    var receiptStoreId: Long

,
@ColumnInfo(name = "receipt_dep_id")
var receiptDepId: Long
)
{

   fun getEmpString():String {
       return receiptEmpId.toString()
   }



}

