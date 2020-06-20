package com.example.zaitoneh.database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

//@Entity(tableName="items_table")
@Entity(tableName="items_table", indices = arrayOf(Index(value = ["item_type_level1", "item_type_level2","item_type_level2","item_name"],
    unique = true)))

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
    @ColumnInfo(name = "item_type_level6")
    var itemLevel6: String = ""
)

{
    override fun toString(): String {
        return "id = " + itemId + "  main name = " + itemMain + "  level 1 = " + itemLevel1 + "  level 2 = " + itemLevel2  + "  level 3 = " + itemLevel3
    }


}