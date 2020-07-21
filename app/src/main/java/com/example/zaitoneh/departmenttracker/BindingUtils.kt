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

package com.example.zaitoneh.departmenttracker

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
import com.example.zaitoneh.database.Department
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.one_department.view.*
import kotlinx.android.synthetic.main.fragment_deparment_detail.view.*


@BindingAdapter("departmentId")
fun TextView.setdepartmentId(department: Department?) {
    department?.let {
        text = department.departmentId.toString()
    }
}
@BindingAdapter("departmentCodeFormatted")
fun TextView.setdepartmentCodeFormatted(department: Department?) {
    department?.let {
        text = department.departmentCode


    }
}

@BindingAdapter("departmentNotFormatted")
fun TextView.setdepartmentNotFormatted(department: Department?) {
    department?.let {
        text = department.departmentNote


    }
}



@BindingAdapter("departmentNameFormatted")
fun TextView.setdepartmentNameFormatted(department: Department?) {
    department?.let {
        text =  convertDurationToFormatted(department.departmentName, department.departmentName, context.resources)


    }
}