package com.example.zaitoneh.employeedetail

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.zaitoneh.R

import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.database.EmployeeDatabaseDao
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentEmployeeDetailBinding

//import com.example.zaitoneh.employeedetail.EmployeeDetailFragmentArgs
import com.example.zaitoneh.employeedetail.EmployeeDetailViewModel
import com.example.zaitoneh.employeedetail.EmployeeDetailViewModelFactory
import kotlinx.coroutines.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEmployeeDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_employee_detail, container, false
        )
        var employee: Employee = Employee()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).employeeDatabaseDao
      val args = EmployeeDetailFragmentArgs.fromBundle(requireArguments())
        Log.i("employee","args =" + args.employeeId )
        if (args.employeeId==0L) binding.saveEmployeeButton.text=this.context?.resources?.getString(R.string.save)
       else{
         binding.saveEmployeeButton.text=this.context?.resources?.getString(R.string.update)

       }

     val viewModelFactory = EmployeeDetailViewModelFactory(args.employeeId, dataSource)



        val employeeDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(EmployeeDetailViewModel::class.java)


        binding.employeeDetailViewModel = employeeDetailViewModel

        binding.setLifecycleOwner(this)

        binding.employee=Employee()

        //**************************************


        employeeDetailViewModel.updateEmployeeToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "This employee is updated", Toast.LENGTH_LONG
                ).show()
              view?.findNavController()?.navigate(R.id.action_employeeDetailFragment_to_employeeTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error This employee is not  updated",
                        Toast.LENGTH_LONG
                    ).show()

            }
        })

        employeeDetailViewModel.deleteEmployeeFromDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "The employee has been deleted", Toast.LENGTH_LONG
                ).show()
               view?.findNavController()?.navigate(R.id.action_employeeDetailFragment_to_employeeTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error, The employee wasn't deleted",
                        Toast.LENGTH_LONG
                    ).show()
            }
        })








        employeeDetailViewModel.saveEmployeeToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                binding.employee= Employee()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "This employee is already exist", Toast.LENGTH_LONG
                    ).show()

            }
        })

        employeeDetailViewModel.employeeValidation.observe(this, Observer {
            if (it == false) { // Observed state is true.
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Please Fill all fields", Toast.LENGTH_LONG
                    ).show()
                employeeDetailViewModel.setSaveEmployeeToDataBase()
            }
        })




        binding.backEmployeeButton.setOnClickListener {

         it.findNavController().navigate(R.id.action_employeeDetailFragment_to_employeeTrackerFragment)

        }

        binding.employee=Employee()


        return binding.root
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
           // fragmentService.fragmentsCount--
            //Called when user should be navigated back
            callback.isEnabled = false
            dispatcher.onBackPressed()
        }
    }


}

