package com.example.zaitoneh.suppliertracker


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
import com.example.zaitoneh.database.Supplier
import com.jakewharton.rxbinding2.widget.checked
import kotlinx.android.synthetic.main.one_supplier.view.*

@BindingAdapter("supplierId")
fun TextView.setsupplierId(supplier: Supplier?) {
    supplier?.let {
        text = supplier.supId.toString()
    }
}

@BindingAdapter("supplierNameFormatted")
fun TextView.setsupplierNameFormatted(supplier: Supplier?) {
    supplier?.let {
        text =  convertDurationToFormatted(supplier.supFullName, supplier.supFullName, context.resources)


    }
}

@BindingAdapter("supplierAddressFormatted")
fun TextView.setsupaddressFormatted(supplier: Supplier?) {
    supplier?.let {
        text = convertNumericQualityToString(supplier.supAddress, context.resources)
    }
}
@BindingAdapter("supplierMobile1Formatted")
fun TextView.setsupmobile1Formatted(supplier: Supplier?) {
    supplier?.let {
        text = convertNumericQualityToString(supplier.supMobile1, context.resources)
    }
}

@BindingAdapter("supplierMobile2Formatted")
fun TextView.setsupmobile2Formatted(supplier: Supplier?) {
    supplier?.let {
        text = convertNumericQualityToString(supplier.supMobile2, context.resources)
    }
}