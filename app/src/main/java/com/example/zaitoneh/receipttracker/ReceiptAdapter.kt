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


package com.example.zaitoneh.receipttracker
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Receipt
import com.example.zaitoneh.databinding.OneReceiptBinding
import com.example.zaitoneh.employeetracker.toDate
import java.text.SimpleDateFormat

class ReceiptAdapter(val clickListener: ReceiptListener) : ListAdapter <Receipt,ReceiptAdapter.ViewHolder> (ReceiptDiffCallback()) , Filterable {
    lateinit var mLstReceipt: MutableList<Receipt>
    lateinit var mFilteredList: MutableList<Receipt>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receipt = getItem(position)

        holder.bind(clickListener,receipt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneReceiptBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ReceiptListener,receipt: Receipt) {
            binding.receipt = receipt
            binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneReceiptBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstReceipt: MutableList<Receipt>) {
        mLstReceipt = lstReceipt
        mFilteredList=lstReceipt

    }

    /*********************************/

    override fun getFilter(): Filter {

        return object : Filter() {
         override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

             val charString = charSequence.toString()

             if (charString.isEmpty()) {
                 mFilteredList = mLstReceipt
             } else {

                 val filteredList = mLstReceipt.filter {  ( it.receiptId.toString() + " " + it.receiptSupId.toString() + it.receiptStoreId.toString() +"  " + convertLongToTime(it.receiptDate) ).toLowerCase().contains(charString.toLowerCase()) }
                     .toMutableList()
                     mFilteredList = filteredList

             }



             val filterResults = Filter.FilterResults()
             filterResults.values = mFilteredList
             return filterResults
         }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            submitList(filterResults.values as MutableList<Receipt>)
            notifyDataSetChanged()
        }
    }


    }
    /*********************************************/

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {

        val date = toDate(time)

        // val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val format = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss")
        return format.format(date)
    }


}



/**
 * Callback for calculating the diff between two non-null receipts in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ReceiptDiffCallback : DiffUtil.ItemCallback<Receipt>() {
    override fun areItemsTheSame(oldItem: Receipt, newItem: Receipt): Boolean {
        return oldItem.receiptId == newItem.receiptId
    }

    override fun areContentsTheSame(oldItem: Receipt, newItem: Receipt): Boolean {
        return oldItem == newItem
    }


}
class ReceiptListener(val clickListener: (receiptId: Long) -> Unit) {

    fun onClick(receipt: Receipt) = clickListener(receipt.receiptId)

}
