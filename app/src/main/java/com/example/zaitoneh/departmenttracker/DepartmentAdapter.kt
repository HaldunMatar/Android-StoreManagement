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


package com.example.zaitoneh.departmenttracker
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.Department
import com.example.zaitoneh.databinding.OneDepartmentBinding

class DepartmentAdapter(val clickListener: DepartmentListener) : ListAdapter <Department,DepartmentAdapter.ViewHolder> (DepartmentDiffCallback()) , Filterable {
    lateinit var mLstDepartment: MutableList<Department>
    lateinit var mFilteredList: MutableList<Department>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val department = getItem(position)

        holder.bind(clickListener,department)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneDepartmentBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: DepartmentListener,department: Department) {
            binding.department = department
           binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneDepartmentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstDepartment: MutableList<Department>) {
        mLstDepartment = lstDepartment
        mFilteredList=lstDepartment

    }

    /*********************************/

    override fun getFilter(): Filter {

        return object : Filter() {
         override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

             val charString = charSequence.toString()

             if (charString.isEmpty()) {
                 mFilteredList = mLstDepartment
             } else {

                 val filteredList = mLstDepartment.filter { (it.departmentName+" " +it.departmentCode).toLowerCase().contains(charString.toLowerCase()) }
                     .toMutableList()
                     mFilteredList = filteredList


             }

             val filterResults = Filter.FilterResults()
             filterResults.values = mFilteredList
             return filterResults
         }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            submitList(filterResults.values as MutableList<Department>)
           notifyDataSetChanged()
        }
    }


    }
    /*********************************************/


}

/**
 * Callback for calculating the diff between two non-null departments in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class DepartmentDiffCallback : DiffUtil.ItemCallback<Department>() {
    override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem.departmentId == newItem.departmentId
    }

    override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem == newItem
    }


}
class DepartmentListener(val clickListener: (sleepId: Long) -> Unit) {

    fun onClick(department: Department) = clickListener(department.departmentId)

}
