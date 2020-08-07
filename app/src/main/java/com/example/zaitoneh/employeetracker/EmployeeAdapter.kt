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


package com.example.zaitoneh.employeetracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.databinding.OneEmployeeBinding

class EmployeeAdapter(val clickListener: EmployeeListener) :
    ListAdapter<Employee, EmployeeAdapter.ViewHolder>(EmployeeDiffCallback()), Filterable {
    lateinit var mLstEmployee: MutableList<Employee>
    lateinit var mFilteredList: MutableList<Employee>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = getItem(position)

        holder.bind(clickListener, employee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: EmployeeListener, employee: Employee) {
            binding.employee = employee
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneEmployeeBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    fun updateList(lstEmployee: MutableList<Employee>) {
        mLstEmployee = lstEmployee
        mFilteredList = lstEmployee

    }

    /*********************************/

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    mFilteredList = mLstEmployee
                } else {

                    val filteredList = mLstEmployee.filter {
                        (it.employeeName + " " + it.employeeCode).toLowerCase()
                            .contains(charString.toLowerCase())
                    }
                        .toMutableList()

                    mFilteredList = filteredList


                }

                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
               // val x: String? = y as String?
                if (filterResults?.values  != null)
                submitList(   filterResults?.values  as MutableList<Employee>?  )
                notifyDataSetChanged()
            }
        }


    }
    /*********************************************/


}

/**
 * Callback for calculating the diff between two non-null employees in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.employeeId == newItem.employeeId
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }


}

class EmployeeListener(val clickListener: (sleepId: Long) -> Unit) {

    fun onClick(employee: Employee) = clickListener(employee.employeeId)

}
