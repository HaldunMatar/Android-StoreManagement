package com.example.zaitoneh.database


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

//@Entity(tableName="items_table")
@Entity(tableName="employee_table", indices = arrayOf(Index(value = ["employee_Full_name", "employee_code"],
    unique = true)))
@Parcelize
data class Employee(
    @PrimaryKey(autoGenerate = true)
    var employeeId: Long = 0L,

    @ColumnInfo(name = "employee_Full_name")
    var employeeName: String = "",

    @ColumnInfo(name="employee_code")
    var employeeCode: String = "",
    @ColumnInfo(name="employee_mobile1")
    var employeeMobile1: String = "",
    @ColumnInfo(name="employee_mobile2")
    var employeeMobile2: String = "",
    @ColumnInfo(name="employee_note")
    var employeeNote: String = "",

    @ColumnInfo(name = "employee_address")
    var employeeAddress: String = ""

) : Parcelable {
    override fun toString():String{

        return employeeId.toString() +"   " + employeeName
    }


}

