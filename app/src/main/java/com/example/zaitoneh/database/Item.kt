package com.example.zaitoneh.database
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

//@Entity(tableName="items_table")
@Entity(tableName="items_table", indices = arrayOf(Index(value = ["item_type_level1", "item_type_level2","item_type_level3","item_name"],
    unique = true)))
@Parcelize


data class Item(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo(name = "item_name")
    var itemMain: String = "",

    @ColumnInfo(name="item_type_level1")
    var itemLevel1: String = "",

    @ColumnInfo(name = "item_type_level2")
    var itemLevel2: String = "",

    @ColumnInfo(name = "item_type_level3")
    var itemLevel3: String = "",

    @ColumnInfo(name = "item_type_level4")
    var itemLevel4: String = "",
    @ColumnInfo(name = "item_type_level5")
    var itemLevel5: String = "",
/*
    @ColumnInfo(name = "last_update")
    var last_update: String = "",*/
    @ColumnInfo(name = "item_type_level6")
    var itemLevel6: String = ""
): Parcelable

{



}