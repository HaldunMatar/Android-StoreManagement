package com.example.zaitoneh.database


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate


@Entity(tableName="receipt_table")
@Parcelize
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    var receiptId: Long = 0L,

    @ColumnInfo(name = "receipt_note")
    var receiptNote: String = "",

    @ColumnInfo(name = "receipt_date")
    var receiptDate: Long = System.currentTimeMillis(),


    @ColumnInfo(name = "receipt_sup_id")
    var receiptSupId: Long=0L,

    @ColumnInfo(name = "receipt_city")
    var receiptCity: String = "",

    @ColumnInfo(name = "receipt_emp_id")
    var receiptEmpId: Long=0L,

    @ColumnInfo(name = "receipt_dep_id")
    var receiptDepId: Long=0L,

    @ColumnInfo(name = "receipt_store_id")
    var receiptStoreId: Long=0L
): Parcelable
{



}


