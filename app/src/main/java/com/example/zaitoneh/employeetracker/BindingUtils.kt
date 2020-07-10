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
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.zaitoneh.R
import com.example.zaitoneh.convertDurationToFormatted
import com.example.zaitoneh.convertNumericQualityToString
import com.example.zaitoneh.database.Employee
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.one_employee.view.*
import kotlinx.android.synthetic.main.fragment_employee_detail.view.*

@BindingAdapter("employeeId")
fun TextView.setemployeeId(employee: Employee?) {
    employee?.let {
        text = employee.employeeId.toString()
    }
}



@BindingAdapter("employeeMobile1Formatted")
fun TextView.setemployeeMobile1Formatted(employee: Employee?) {
    employee?.let {
        text =  employee.employeeMobile1


    }
}

@BindingAdapter("employeeMobile2Formatted")
fun TextView.setemployeeMobile2Formatted(employee: Employee?) {
    employee?.let {
        text = employee.employeeMobile2


    }
}


@BindingAdapter("employeeAddressFormatted")
fun TextView.setemployeeAddressFormatted(employee: Employee?) {
    employee?.let {
        text = employee.employeeAddress


    }
}

@BindingAdapter("employeeNoteFormatted")
fun TextView.setemployeeNoteFormatted(employee: Employee?) {
    employee?.let {
        text = employee.employeeNote


    }
}


@BindingAdapter("employeeNameFormatted")
fun TextView.setemployeeNameFormatted(employee: Employee?) {
    employee?.let {
        text =  convertDurationToFormatted(employee.employeeName, employee.employeeName, context.resources)


    }
}

@BindingAdapter("employeeCodeFormatted")
fun TextView.setemployeeCodeFormatted(employee: Employee?) {
    employee?.let {
        text = convertNumericQualityToString(employee.employeeCode, context.resources)
    }
}

@BindingAdapter("employeeAddressFormatted")
fun TextView.setaddressFormatted(employee: Employee?) {
    employee?.let {
        text = convertNumericQualityToString(employee.employeeAddress, context.resources)
    }
}
