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

package com.example.zaitoneh.storetracker

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
import com.example.zaitoneh.database.Store
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.one_store.view.*

@BindingAdapter("storeId")
fun TextView.setstoreId(store: Store?) {
    store?.let {
        text = store.storeId.toString()
    }
}



@BindingAdapter("storeNameFormatted")
fun TextView.setstoreNameFormatted(store: Store?) {
    store?.let {
        text =  convertDurationToFormatted(store.storeName, store.storeName, context.resources)


    }
}

@BindingAdapter("storeCodeFormatted")
fun TextView.setstoreCodeFormatted(store: Store?) {
    store?.let {
        text = convertNumericQualityToString(store.storeCode, context.resources)
    }
}

@BindingAdapter("addressFormatted")
fun TextView.setaddressFormatted(store: Store?) {
    store?.let {
        text = convertNumericQualityToString(store.storeAddress, context.resources)
    }
}
