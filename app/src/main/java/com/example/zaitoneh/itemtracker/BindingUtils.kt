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

package com.example.zaitoneh.itemtracker

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.zaitoneh.R
import com.example.zaitoneh.convertDurationToFormatted
import com.example.zaitoneh.convertNumericQualityToString
import com.example.zaitoneh.database.Item
import kotlinx.android.synthetic.main.one_item.view.*

@BindingAdapter("itemImage")
fun ImageView.setItemImage(item: Item?) {
    Log.i("bindingimage",item?.itemMain.toString())
    item?.let {
        setImageResource(when (item.itemMain) {

           "2131361929" -> R.drawable.ic_lamp
            "2131361930"->R.drawable.ic_rocket
            "2131361931"->R.drawable.ic_project

            else -> R.drawable.ic_boxdropboxicon
        })
    }
}

@BindingAdapter("level1Formatted")
fun TextView.setlevel1Formatted(item: Item?) {
    item?.let {
        text =  convertDurationToFormatted(item.itemLevel1, item.itemLevel1, context.resources)


    }
}

@BindingAdapter("level2Formatted")
fun TextView.setlevel2Formatted(item: Item?) {
    item?.let {
        text = convertNumericQualityToString(item.itemLevel2, context.resources)
    }
}
