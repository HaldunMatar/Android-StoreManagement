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

package com.example.zaitoneh.receipt

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import com.example.zaitoneh.database.*
import com.example.zaitoneh.employeetracker.EmployeeTrackerViewModel


@BindingAdapter("receiptdetailItemFormatted")
fun TextView.setItemFormatted(receiptdetail: ReceiptDetail?) {
    receiptdetail?.let {
        text = receiptdetail.itemId.toString()
    }
}
@BindingAdapter("receiptdetailPriceFormatted")
fun TextView.setPriceFormatted(receiptdetail: ReceiptDetail?) {
    receiptdetail?.let {
        text = receiptdetail.itemPrice.toString()
    }
}
@BindingAdapter("receiptdetailAmountFormatted")
fun TextView.setAmountFormatted(receiptdetail: ReceiptDetail?) {
    receiptdetail?.let {
        text = receiptdetail.amount.toString()
    }
}

@BindingAdapter("receiptreceiptNoteFormatted")
fun TextView.setreceiptreceiptNoteFormatted(receipt: Receipt?) {
    Log.i("receiptreceiptNote" , receipt?.receiptNote.toString() )
    if (receipt != null) {
        Log.i("receiptreceiptNote" ,  " not null "+receipt.receiptNote.toString() )
    }
    receipt?.let {
        text = receipt.receiptNote.toString()
    }
}


@BindingAdapter("receiptDepatmentFormatted")
fun Spinner.setreceiptDepatmentFormatted(receipt: Receipt?) {
    Log.i("DepatmentFormatted", receipt.toString())
    var Index : Int  = getIndex(this,receipt?.receiptDepId.toString()) ;
    setSelection(Index)
}


@BindingAdapter("receiptEmployeeFormatted" ,"receiptEmployeeFormattedSelected")
fun Spinner.setreceiptEmployeeFormatted(receipt: List<Employee>? , receipt1: Receipt?) {
   if (receipt.isNullOrEmpty()) {

    }else{
       val adapter =
           ArrayAdapter(
               this.context,
               android.R.layout.simple_spinner_item,
               receipt!!.toTypedArray()
           )
       this.adapter = adapter
   }
       var Index : Int  = getIndex(this,receipt1?.receiptEmpId.toString()) ;
       Log.i("EmployeeFormattedSele", receipt1.toString())
       setSelection(Index)


}



@BindingAdapter("receiptSupplierFormatted")
fun Spinner.setreceiptSupplierFormatted(receipt: Receipt?) {
    var Index : Int  = getIndex(this,receipt?.receiptSupId.toString()) ;
    setSelection(Index)
}



@BindingAdapter("receiptStoreFormatted")
fun Spinner.setreceiptStoreFormatted(receipt: Receipt?) {
    var Index : Int  = getIndex(this,receipt?.receiptStoreId.toString()) ;
      setSelection(Index)
}
private  fun getIndex(spinner: Spinner, myString: String): Int {
    var index = 0

     var valueindex :String=""

    for (i in 0 until spinner.count) {

        when (spinner.getItemAtPosition(i)) {
            is Store -> valueindex = (spinner.getItemAtPosition(i) as Store).storeId.toString()
            is Supplier ->valueindex = (spinner.getItemAtPosition(i) as Supplier).supId.toString()
            is Employee ->valueindex = (spinner.getItemAtPosition(i) as Employee).employeeId.toString()
            is Department ->valueindex = (spinner.getItemAtPosition(i) as Department).departmentId.toString()
        }

        if (valueindex.equals(myString) ) {
            index = i
            Log.i("spinner" ,i.toString() )
        }
    }

    return index
}