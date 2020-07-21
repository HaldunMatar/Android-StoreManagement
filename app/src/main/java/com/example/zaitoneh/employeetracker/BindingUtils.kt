/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.zaitoneh.employeetracker

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.TypeConverter
import com.example.zaitoneh.database.Receipt
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setreceiptStoreFormatted")
fun TextView.setreceiptStoreFormatted(receipt: Receipt?) {
    receipt?.let {
        text = receipt.receiptStoreId.toString()
    }
}



@BindingAdapter("setreceiptCodeFormatted")
fun TextView.setreceiptCodeFormatted(receipt: Receipt?) {
    receipt?.let {
        text =  receipt.receiptId.toString()


    }
}

@BindingAdapter("setreceiptSupplierFormatted")
fun TextView.setreceiptSupplierFormatted(receipt: Receipt?) {
    receipt?.let {
        text = receipt.receiptSupId.toString()


    }
}

@TypeConverter
fun toDate(dateLong:Long):Date {
    return Date(dateLong)
}

@TypeConverter
fun fromDate(date: Date):Long{
    return date.time;
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {

    val date = toDate(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(1595355165)
}
@BindingAdapter("setreceiptDateFormatted")
fun TextView.setreceiptDateFormatted(receipt: Receipt?) {
    receipt?.let {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        text =receipt.receiptDate.toString()
        Log.i("date", convertLongToDateString(1595355165))
    }
}


