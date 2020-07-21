package com.example.zaitoneh.database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(tableName="department_table", indices = arrayOf(Index(value = ["department_name", "department_code"],
    unique = true)))

data class Department(
    @PrimaryKey(autoGenerate = true)
    var departmentId: Long = 0L,

    @ColumnInfo(name = "department_name")
    var departmentName: String = "",

    @ColumnInfo(name="department_code")
    var departmentCode: String = "",

    @ColumnInfo(name="department_note")
    var departmentNote: String = ""



){
    override fun toString():String{

        return departmentName
    }


}


