package com.example.zaitoneh.database
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

//@Entity(tableName="items_table")
@Entity(tableName="store_table", indices = arrayOf(Index(value = ["store_name", "store_code"],
    unique = true)))
@Parcelize
data class Store(
    @PrimaryKey(autoGenerate = true)
    var storeId: Long = 0L,




    @ColumnInfo(name = "store_name")
    var storeName: String = "",

    @ColumnInfo(name="store_code")
    var storeCode: String = "",

    @ColumnInfo(name = "store_address")
    var storeAddress: String = ""



): Parcelable {

}