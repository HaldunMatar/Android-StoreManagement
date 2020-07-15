package com.example.zaitoneh.receipt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Employee
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentReceiptDetailBinding
import com.example.zaitoneh.databinding.FragmentReceiptDetailBindingImpl
import com.example.zaitoneh.databinding.FragmentReceiptTrackerBindingImpl
import com.example.zaitoneh.databinding.FragmentStoreDetailBinding
import com.example.zaitoneh.employeedetail.EmployeeDetailViewModel
import com.example.zaitoneh.storedetail.StoreDetailViewModel
import com.example.zaitoneh.storedetail.StoreDetailViewModelFactory
import com.example.zaitoneh.supplierdetail.SupplierDetailViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReceiptDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceiptDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentReceiptDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_receipt_detail, container, false
        )


        // access the items of the list

        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).receiptDatabaseDao
        val viewModelFactory = ReceiptDetailViewModelFactory(0, dataSource)
        val receiptDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReceiptDetailViewModel::class.java)
        val dataSourceEmp = StoreDatabase.getInstance(application).employeeDatabaseDao
        val  employeeDetailViewModel : EmployeeDetailViewModel  =EmployeeDetailViewModel(0,dataSourceEmp)

        val dataSourceSup = StoreDatabase.getInstance(application).supplierDatabaseDao
        val   supplierDetailViewModel : SupplierDetailViewModel  =SupplierDetailViewModel(0,dataSourceSup)


        val supplier = supplierDetailViewModel.getSuppliers()
        Log.i("ArrayAdapter",supplierDetailViewModel.sups.toString())
        val employees = employeeDetailViewModel.getEmployees()
        val languages  = resources.getStringArray(R.array.Languages)
        val empspinner = binding.receiptEmpInput

        if (empspinner != null) {
          val adapter =
               ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,employeeDetailViewModel.emps.toTypedArray())

            empspinner.adapter = adapter
        }

        val supspinner = binding.receiptSupInput

        if (supspinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,supplierDetailViewModel.sups.toTypedArray())

            supspinner.adapter = adapter
        }

        return binding.root
    }


}