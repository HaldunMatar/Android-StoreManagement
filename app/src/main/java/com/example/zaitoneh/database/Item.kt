package com.example.zaitoneh.database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName="items_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo(name = "item_name")
    val itemMain: String = "name",

    @ColumnInfo(name="item_type_level1")
    var itemLevel1: String = "Type1",

    @ColumnInfo(name = "item_type_level2")
    var itemLevel2: String = "Type2",

    @ColumnInfo(name = "item_type_level3")
    var itemLevel3: String = "Type3",

    @ColumnInfo(name = "item_type_level4")
    var itemLevel4: String = "Type4"
)