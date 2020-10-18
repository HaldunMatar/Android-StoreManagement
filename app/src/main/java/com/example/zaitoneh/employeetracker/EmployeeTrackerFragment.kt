package com.example.zaitoneh.employeetracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.zaitoneh.database.Employee
//import com.example.zaitoneh.employeetracker.EmployeeTrackerViewModel

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentEmployeeTrackerBinding




class EmployeeTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.EmployeeTracker)
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
            if(it!=null) {
                Log.i("navigateToEditEmployee", it.toString())
                   view?.findNavController()?.navigate(
                       EmployeeTrackerFragmentDirections.actionEmployeeTrackerFragmentToEmployeeDetailFragment()
                           .setEmployeeId(it)
                   )
                  employeeTrackerViewModel.onEmployeeClicked(null)
            }

            }
        })


       binding.employeeList.adapter = adapter


      employeeTrackerViewModel.list.observe(viewLifecycleOwner, Observer {
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
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    lateinit var dispatcher : OnBackPressedDispatcher
    lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dispatcher = requireActivity().onBackPressedDispatcher
        callback = dispatcher.addCallback(
            //Lifecycle owner
            this
        ) {


            //Called when user should be navigated back
            callback.isEnabled = false
            dispatcher.onBackPressed()
        }
    }
}



