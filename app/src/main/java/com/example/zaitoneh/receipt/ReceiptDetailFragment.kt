package com.example.zaitoneh.receipt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Receipt
import com.example.zaitoneh.database.*
import com.example.zaitoneh.databinding.FragmentReceiptDetailBinding
import com.example.zaitoneh.employeedetail.EmployeeDetailViewModel
import com.example.zaitoneh.storedetail.StoreDetailViewModel
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
class ReceiptDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentReceiptDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_receipt_detail, container, false
        )


        // access the items of the list

        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).receiptDatabaseDao
        val viewModelFactory = ReceiptDetailViewModelFactory(0, dataSource)
        val receiptDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReceiptDetailViewModel::class.java)
       binding.receiptDetailViewModel= receiptDetailViewModel

        val dataSourceEmp = StoreDatabase.getInstance(application).employeeDatabaseDao
        val  employeeDetailViewModel : EmployeeDetailViewModel  =EmployeeDetailViewModel(0,dataSourceEmp)

        val dataSourceSup = StoreDatabase.getInstance(application).supplierDatabaseDao
        val   supplierDetailViewModel : SupplierDetailViewModel  =SupplierDetailViewModel(0,dataSourceSup)


        val supplier = supplierDetailViewModel.getSuppliers()


        Log.i("ArrayAdapter",supplierDetailViewModel.sups.toString())
        val employees = employeeDetailViewModel.getEmployees()
        val supspinner = binding.receiptSupInput
        val languages  = resources.getStringArray(R.array.Languages)
        val empspinner = binding.receiptEmpInput
        val storspinner = binding.receiptStoreInput
        empspinner.setOnItemSelectedListener(this)
        supspinner.setOnItemSelectedListener(this)
        storspinner.setOnItemSelectedListener(this)

        if (empspinner != null) {
          val adapter =
               ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,employeeDetailViewModel.emps.toTypedArray())
            empspinner.adapter = adapter
        }


        if (supspinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,supplierDetailViewModel.sups.toTypedArray())

            supspinner.adapter = adapter
        }

        val dataSourceStore = StoreDatabase.getInstance(application).storeDatabaseDao
        val   storeDetailViewModel : StoreDetailViewModel  =StoreDetailViewModel(0,dataSourceStore)

        storeDetailViewModel.getStores()
        if (storspinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,storeDetailViewModel.stores.toTypedArray())
            storspinner.adapter = adapter
        }


       val receipt= Receipt(0,"", 12555585,0,"",0,0)
        binding.receipt=receipt
        return binding.root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent!!.getItemAtPosition(position).toString()
        val spinnerobj= parent!!.getItemAtPosition(position)

        when (spinnerobj) {
            is Employee -> binding?.receipt?.receiptEmpId=spinnerobj.employeeId
            is Supplier -> {
                binding?.receipt?.receiptSupId=spinnerobj.supId
            }
            is Store -> binding?.receipt?.receiptStoreId=spinnerobj.storeId
        }


        Toast.makeText(parent.context, text, Toast.LENGTH_SHORT).show()


    }


}