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

    if (receipt != null) {

    }
    receipt?.let {
        text = receipt.receiptNote.toString()
    }
}



@BindingAdapter("receiptDepatmentFormatted" ,"receiptDepatmentFormattedSelected")
fun Spinner.setreceiptDepatmentFormatted(receiptList: List<Department>? , receipt1: Receipt?) {
    if (receiptList.isNullOrEmpty()) {

    }else{
        val adapter =
            ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                receiptList!!.toTypedArray()
            )
        this.adapter = adapter
    }
    var Index : Int  = getIndex(this,receipt1?.receiptDepId.toString()) ;

    setSelection(Index)


}




@BindingAdapter("receiptDepatmentFormatted")
fun Spinner.setreceiptDepatmentFormatted1(receipt: Receipt?) {

    var Index : Int  = getIndex(this,receipt?.receiptDepId.toString()) ;
    setSelection(Index)
}


@BindingAdapter("receiptEmployeeFormatted" ,"receiptEmployeeFormattedSelected")
fun Spinner.setreceiptEmployeeFormatted(receiptList: List<Employee>? , receipt1: Receipt?) {
   if (receiptList.isNullOrEmpty()) {

    }else{
       val adapter =
           ArrayAdapter(
               this.context,
               android.R.layout.simple_spinner_item,
               receiptList!!.toTypedArray()
           )
       this.adapter = adapter
   }
       var Index : Int  = getIndex(this,receipt1?.receiptEmpId.toString()) ;

       setSelection(Index)


}


@BindingAdapter("receiptSupplierFormatted" ,"receiptSupplierFormattedSelected")
fun Spinner.setreceiptSupplierFormatted(receiptList: List<Supplier>? , receipt1: Receipt?) {
    if (receiptList.isNullOrEmpty()) {

    }else{
        val adapter =
            ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                receiptList!!.toTypedArray()
            )
        this.adapter = adapter
    }
    var Index : Int  = getIndex(this,receipt1?.receiptSupId.toString()) ;

    setSelection(Index)


}





@BindingAdapter("receiptSupplierFormatted")
fun Spinner.setreceiptSupplierFormatted1(receipt: Receipt?) {
    var Index : Int  = getIndex(this,receipt?.receiptSupId.toString()) ;
    setSelection(Index)
}

@BindingAdapter("receiptItemsFormatted")
fun Spinner.setreceiptItemsFormatted(receiptList: List<Item>? ) {
    if (receiptList.isNullOrEmpty()) {

    }else{
        val adapter =
            ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                receiptList!!.toTypedArray()
            )
        this.adapter = adapter
    }



}



@BindingAdapter("receiptStoreFormatted" ,"receiptStoreFormattedSelected")
fun Spinner.setreceiptStoreFormatted(receiptList: List<Store>? , receipt1: Receipt?) {
    if (receiptList.isNullOrEmpty()) {

    }else{
        val adapter =
            ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                receiptList!!.toTypedArray()
            )
        this.adapter = adapter
    }
    var Index : Int  = getIndex(this,receipt1?.receiptStoreId.toString()) ;

    setSelection(Index)


}

@BindingAdapter("receiptStoreFormatted")
fun Spinner.setreceiptStoreFormatted1(receipt: Receipt?) {
  //  var Index : Int  = getIndex(this,receipt?.receiptStoreId.toString()) ;
   //   setSelection(Index)
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

        }
    }

    return index
}