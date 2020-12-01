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

package  com.example.zaitoneh.employeetracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zaitoneh.database.EmployeeDatabaseDao
import com.example.zaitoneh.employeetracker.EmployeeTrackerViewModel
/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
/*class EmployeeTrackerViewModelFactory(
        private val dataSource: EmployeeDatabaseDao,

        private val application: Application) : ViewModelProvider.Factory {*/

class EmployeeTrackerViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeTrackerViewModel::class.java)) {
            return EmployeeTrackerViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

