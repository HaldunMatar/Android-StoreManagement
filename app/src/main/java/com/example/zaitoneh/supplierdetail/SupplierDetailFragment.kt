package com.example.zaitoneh.supplierdetail

import android.app.Dialog
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.supplierdetail.SupplierDetailFragmentArgs

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.Supplier
import com.example.zaitoneh.database.SupplierDatabaseDao
import com.example.zaitoneh.databinding.FragmentSupplierDetailBinding

//import com.example.zaitoneh.supplierdetail.SupplierDetailFragmentArgs
import com.example.zaitoneh.supplierdetail.SupplierDetailViewModel
import com.example.zaitoneh.supplierdetail.SupplierDetailViewModelFactory
import kotlinx.coroutines.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SupplierDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SupplierDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.SupplierDetails)
        // Inflate the layout for this fragment
        val binding: FragmentSupplierDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_supplier_detail, container, false
        )
        var supplier: Supplier = Supplier()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).supplierDatabaseDao
        val args = SupplierDetailFragmentArgs.fromBundle(requireArguments())
        Log.i("SupplierID",args.supplierId.toString())
        if (args.supplierId==0L) binding.saveSupplierButton.text=this.context?.resources?.getString(R.string.save)
        else{
            binding.saveSupplierButton.text=this.context?.resources?.getString(R.string.update)

        }

        val viewModelFactory = SupplierDetailViewModelFactory(args.supplierId, dataSource)

        val supplierDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(SupplierDetailViewModel::class.java)


        binding.supplierDetailViewModel = supplierDetailViewModel
        binding.setLifecycleOwner(this)

        binding.supplier=Supplier()

        //**************************************


        supplierDetailViewModel.updateSupplierToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "This supplier is updated", Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.navigate(R.id.action_supplierDetailFragment_to_supplierTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error This supplier is not  updated",
                        Toast.LENGTH_LONG
                    ).show()

            }
        })

        supplierDetailViewModel.deleteSupplierFromDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "The supplier has been deleted", Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.navigate(R.id.action_supplierDetailFragment_to_supplierTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error, The supplier wasn't deleted",
                        Toast.LENGTH_LONG
                    ).show()
            }
        })




        supplierDetailViewModel.saveSupplierToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                binding.supplier= Supplier()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "This supplier is already exist", Toast.LENGTH_LONG
                    ).show()

            }
        })

        supplierDetailViewModel.supplierValidation.observe(this, Observer {
            if (it == false) { // Observed state is true.
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Please Fill all fields", Toast.LENGTH_LONG
                    ).show()
                supplierDetailViewModel.setSaveSupplierToDataBase()
            }
        })




        binding.backSupplierButton.setOnClickListener {

            it.findNavController().navigate(R.id.action_supplierDetailFragment_to_supplierTrackerFragment)

        }

        binding.supplier=Supplier()

        /* binding.saveSupplierButton.setOnClickListener {
 
            supplierDetailViewModel.onCreateSupplier(Supplier(11,"GOP1","01541","adress1"))
         }*/

        //******************************************

        return binding.root
    }

}

  