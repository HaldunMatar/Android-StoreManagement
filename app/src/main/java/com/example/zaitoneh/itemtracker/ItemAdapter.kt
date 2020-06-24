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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.databinding.OneItemBinding

//(private var mLstUser: MutableList<User>, private val callback: (Int) -> Unit)
//class ItemAdapter (private var mLstUser: MutableList<Item>): ListAdapter <Item,ItemAdapter.ViewHolder> (ItemDiffCallback()) , Filterable {
//class ItemAdapter : ListAdapter <Item,ItemAdapter.ViewHolder> (ItemDiffCallback()) , Filterable {
class ItemAdapter : ListAdapter <Item,ItemAdapter.ViewHolder> (ItemDiffCallback()) , Filterable {
    var countryFilterList = ArrayList<String>()






    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstItem: MutableList<Item>) {
        mLstItem = lstItem
        mFilteredList=lstItem
    }
    lateinit var mLstItem: MutableList<Item>
    lateinit var mFilteredList: MutableList<Item>
    override fun getFilter(): Filter {
         /*********************************/
        return object : Filter() {
         override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

             val charString = charSequence.toString()

             if (charString.isEmpty()) {
                 mFilteredList = mLstItem
             } else {

                 val filteredList = mLstItem
                     .filter { it.itemLevel1?.toLowerCase()?.contains(charString)!! }
                     .toMutableList()

                 mFilteredList = filteredList
             }

             val filterResults = Filter.FilterResults()
             filterResults.values = mFilteredList
             return filterResults
         }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            submitList(filterResults.values as MutableList<Item>)
            notifyDataSetChanged()
        }
    }

        /*********************************************/
    }


}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
