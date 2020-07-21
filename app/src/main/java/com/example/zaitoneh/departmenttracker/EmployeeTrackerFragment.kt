package com.example.zaitoneh.departmenttracker

import android.os.Bundle
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
import com.example.zaitoneh.database.Department
//import com.example.zaitoneh.departmenttracker.DepartmentTrackerViewModel

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentDepartmentTrackerBinding




class DepartmentTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDepartmentTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_department_tracker, container, false)




        /****************************************************************************/
        val application = requireNotNull(this.activity).application

        val dataSource = StoreDatabase.getInstance(application).departmentDatabaseDao

        val viewModelFactory = DepartmentTrackerViewModelFactory(dataSource, application)

       val departmentTrackerViewModel =
          ViewModelProviders.of(
              this, viewModelFactory).get(DepartmentTrackerViewModel::class.java)

       binding.departmentTrackerViewModel = departmentTrackerViewModel

        binding.setLifecycleOwner(this)



        /******************************************/
       val adapter = DepartmentAdapter(DepartmentListener { departmentId ->
            departmentTrackerViewModel.onDepartmentClicked(departmentId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




      departmentTrackerViewModel.navigateToEditDepartment.observe(this, Observer { department ->
            department?.let {

                view?.findNavController()?.navigate(DepartmentTrackerFragmentDirections.actionDepartmentTrackerFragmentToDepartmentDetailFragment().setDepartmentId(it))
            }
        })


       binding.departmentList.adapter = adapter


      departmentTrackerViewModel.departments.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Department>)
                adapter.submitList(it)


            }
        })


            binding.addDepartmentButton.setOnClickListener {


                it.findNavController().navigate(DepartmentTrackerFragmentDirections.actionDepartmentTrackerFragmentToDepartmentDetailFragment())

            }

        val department_search= binding.departmentSearch
       department_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

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



