package com.example.zaitoneh.suppliertracker

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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Supplier
import com.example.zaitoneh.databinding.OneStoreBinding
import com.example.zaitoneh.databinding.OneSupplierBinding

class SupplierAdapter(val clickListener: SupplierListener) : ListAdapter <Supplier,SupplierAdapter.ViewHolder> (SupplierDiffCallback()) , Filterable {
    lateinit var mLstSupplier: MutableList<Supplier>
    lateinit var mFilteredList: MutableList<Supplier>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supplier = getItem(position)

        holder.bind(clickListener,supplier)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneSupplierBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: SupplierListener,supplier: Supplier) {
            binding.supplier = supplier
            binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneSupplierBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstSupplier: MutableList<Supplier>) {
        mLstSupplier = lstSupplier
        mFilteredList=lstSupplier

    }

    /*********************************/

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    mFilteredList = mLstSupplier
                } else {

                    val filteredList = mLstSupplier.filter { (it.supFullName+" " +it.supMobile1).toLowerCase().contains(charString.toLowerCase()) }
                        .toMutableList()
                    mFilteredList = filteredList


                }

                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                submitList(filterResults.values as MutableList<Supplier>)
                 notifyDataSetChanged()
            }
        }


    }
    /*********************************************/


}

/**
 * Callback for calculating the diff between two non-null stores in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SupplierDiffCallback : DiffUtil.ItemCallback<Supplier>() {
    override fun areItemsTheSame(oldSupplier: Supplier, newSupplier: Supplier): Boolean {
        return oldSupplier.supId == oldSupplier.supId
    }

    override fun areContentsTheSame(oldSupplier: Supplier, newSupplier: Supplier): Boolean {
        return oldSupplier == newSupplier
    }


}
class SupplierListener(val clickListener: (supplierId: Long) -> Unit) {

    fun onClick(supplier: Supplier) = clickListener(supplier.supId)

}
