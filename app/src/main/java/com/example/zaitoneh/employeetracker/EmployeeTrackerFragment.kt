package com.example.zaitoneh.employeetracker

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zaitoneh.database.Employee
//import com.example.zaitoneh.employeetracker.EmployeeTrackerViewModel

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentEmployeeTrackerBinding




class EmployeeTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEmployeeTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_employee_tracker, container, false)




        /****************************************************************************/
        val application = requireNotNull(this.activity).application

        val dataSource = StoreDatabase.getInstance(application).employeeDatabaseDao

        val viewModelFactory = EmployeeTrackerViewModelFactory(dataSource, application)

       val employeeTrackerViewModel =
          ViewModelProviders.of(
              this, viewModelFactory).get(EmployeeTrackerViewModel::class.java)

       binding.employeeTrackerViewModel = employeeTrackerViewModel

        binding.setLifecycleOwner(this)



        /******************************************/
       val adapter = EmployeeAdapter(EmployeeListener { employeeId ->
            employeeTrackerViewModel.onEmployeeClicked(employeeId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




      employeeTrackerViewModel.navigateToEditEmployee.observe(this, Observer { employee ->
            employee?.let {

                view?.findNavController()?.navigate(EmployeeTrackerFragmentDirections.actionEmployeeTrackerFragmentToEmployeeDetailFragment().setEmployeeId(it))
            }
        })


       binding.employeeList.adapter = adapter


      employeeTrackerViewModel.employees.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Employee>)
                adapter.submitList(it)


            }
        })


            binding.addEmployeeButton.setOnClickListener {


                it.findNavController().navigate(EmployeeTrackerFragmentDirections.actionEmployeeTrackerFragmentToEmployeeDetailFragment())

            }

        val employee_search= binding.employeeSearch
       employee_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

          override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)
                return false

            }

        })

        return binding.root
    }


}



