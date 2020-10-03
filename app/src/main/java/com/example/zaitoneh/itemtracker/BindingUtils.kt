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
import com.example.zaitoneh.database.Item
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.one_item.view.*

@BindingAdapter("itemId")
fun TextView.setitemId(item: Item?) {
    item?.let {
        text = item.itemId.toString()
    }
}


@BindingAdapter("itemImage")
fun ImageView.setItemImage(item: Item?) {
    Log.i("bindingimage",item?.itemMain.toString())
    item?.let {
        setImageResource(when (item.itemMain) {

           "Materials" -> R.drawable.ic_butter
            "Box"->R.drawable.ic_box//box

            "Kratin"->R.drawable.ic_carateen

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

@BindingAdapter("level3Formatted")
fun TextView.setlevel3Formatted(item: Item?) {
    item?.let {
        text = convertNumericQualityToString(item.itemLevel3, context.resources)
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("itemMain")
fun RadioGroup.setItemMain(item: Item?) {
    var radioButton1 = findViewById<RadioButton>(R.id.itemMain_box_radio)
    var radioButton2 = findViewById<RadioButton>(R.id.itemMain_kratin_radio)
    var radioButton3 = findViewById<RadioButton>(R.id.itemMain_materials_radio)

    item?.let {
        (when (item.itemMain.toString()) {


            "Materials" -> radioButton3.setChecked(true);
            "Kratin"-> radioButton2.setChecked(true); //karaten
            "Box"-> radioButton1.setChecked(true);////box


            else ->{            }
        })


    }


}
