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
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.databinding.OneStoreBinding

class StoreAdapter(val clickListener: StoreListener) : ListAdapter <Store,StoreAdapter.ViewHolder> (StoreDiffCallback()) , Filterable {
    lateinit var mLstStore: MutableList<Store>
    lateinit var mFilteredList: MutableList<Store>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = getItem(position)
        holder.bind(clickListener,store)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneStoreBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: StoreListener,store: Store) {
            binding.store = store
            binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneStoreBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstStore: MutableList<Store>) {
        mLstStore = lstStore
        mFilteredList=lstStore

    }

    /*********************************/

    override fun getFilter(): Filter {

        return object : Filter() {
         override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

             val charString = charSequence.toString()

             if (charString.isEmpty()) {
                 mFilteredList = mLstStore
             } else {

                 val filteredList = mLstStore.filter { (it.storeName+" " +it.storeCode).toLowerCase().contains(charString.toLowerCase()) }
                     .toMutableList()
                     mFilteredList = filteredList


             }

             val filterResults = Filter.FilterResults()
             filterResults.values = mFilteredList
             return filterResults
         }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            //submitList(filterResults.values as MutableList<Store>)
           // notifyDataSetChanged()
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
class StoreDiffCallback : DiffUtil.ItemCallback<Store>() {
    override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem.storeId == newItem.storeId
    }

    override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem == newItem
    }


}
class StoreListener(val clickListener: (sleepId: Long) -> Unit) {

    fun onClick(store: Store) = clickListener(store.storeId)

}
