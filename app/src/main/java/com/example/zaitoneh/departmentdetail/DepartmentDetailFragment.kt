package com.example.zaitoneh.departmentdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.zaitoneh.R

import com.example.zaitoneh.database.Department
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentDeparmentDetailBinding

//import com.example.zaitoneh.departmentdetail.DepartmentDetailFragmentArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DepartmentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DepartmentDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.DepatmentDetails)
        // Inflate the layout for this fragment
        val binding: FragmentDeparmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_deparment_detail, container, false
        )
        var department: Department = Department()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).departmentDatabaseDao
        val args = DepartmentDetailFragmentArgs.fromBundle(requireArguments())

       if (args.departmentId==0L) binding.saveDepartmentButton.text=  context?.resources?.getString(R.string.Save)
     else{
       binding.saveDepartmentButton.text= context?.resources?.getString(R.string.update)

     }

     val viewModelFactory = DepartmentDetailViewModelFactory(args.departmentId, dataSource)



        val departmentDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(DepartmentDetailViewModel::class.java)


        binding.departmentDetailViewModel = departmentDetailViewModel
        binding.setLifecycleOwner(this)

        binding.department=Department()

        //**************************************


        departmentDetailViewModel.updateDepartmentToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext,context?.resources?.getString(R.string.Updated),Toast.LENGTH_LONG
                ).show()
           view?.findNavController()?.navigate(R.id.action_departmentDetailFragment_to_departmentTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext,context?.resources?.getString(R.string.notUpdated),Toast.LENGTH_LONG
                    ).show()

            }
        })

        departmentDetailViewModel.deleteDepartmentFromDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, context?.resources?.getString(R.string.deteted),Toast.LENGTH_LONG
                ).show()
              view?.findNavController()?.navigate(R.id.action_departmentDetailFragment_to_departmentTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, context?.resources?.getString(R.string.notDeteted),Toast.LENGTH_LONG
                    ).show()
            }
        })








        departmentDetailViewModel.saveDepartmentToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.

                val toast =
                    Toast.makeText(activity!!.applicationContext, context?.resources?.getString(R.string.inserted),Toast.LENGTH_LONG
                    ).show()
                binding.department= Department()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, context?.resources?.getString(R.string.alreadyExist),Toast.LENGTH_LONG
                    ).show()

            }
        })

        departmentDetailViewModel.departmentValidation.observe(this, Observer {
            if (it == false) { // Observed state is true.
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Please Fill all fields", Toast.LENGTH_LONG
                    ).show()
                departmentDetailViewModel.setSaveDepartmentToDataBase()
            }
        })




        binding.backDepartmentButton.setOnClickListener {

        it.findNavController().navigate(R.id.action_departmentDetailFragment_to_departmentTrackerFragment)

        }

        binding.department=Department()


        return binding.root
    }










}

