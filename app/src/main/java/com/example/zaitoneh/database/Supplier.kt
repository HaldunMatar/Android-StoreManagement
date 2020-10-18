package com.example.zaitoneh.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

//@Entity(tableName="supplier_table")
@Entity(tableName="supplier_table", indices = arrayOf(Index(value = ["sup_full_name", "supp_mobile1","supp_mobile2"],
    unique = true)))
@Parcelize
data class Supplier(
    @PrimaryKey(autoGenerate = true)
    var supId: Long = 0L,

    @ColumnInfo(name = "sup_full_name")
    var supFullName: String = "",

    @ColumnInfo(name="supp_mobile1")
    var supMobile1: String = "",

    @ColumnInfo(name = "supp_mobile2")
    var supMobile2: String = "",

    @ColumnInfo(name = "sup_address")
    var supAddress: String = "",
    @ColumnInfo(name = "sup_note")
    var supNote: String = ""

) : Parcelable {
    override fun toString(): String {

        return supFullName
    }

}