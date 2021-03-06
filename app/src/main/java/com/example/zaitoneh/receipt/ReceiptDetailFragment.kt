package com.example.zaitoneh.receipt

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.zaitoneh.MyDialog
import com.example.zaitoneh.R

import com.example.zaitoneh.database.*
import com.example.zaitoneh.databinding.FragmentReceiptDetailBinding
import com.example.zaitoneh.departmentdetail.DepartmentDetailViewModel
import com.example.zaitoneh.receiptDetail.ReceiptDetailAdapter
import com.example.zaitoneh.receiptDetail.ReceiptDetailListener
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
class ReceiptDetailFragment
    : Fragment(), AdapterView.OnItemSelectedListener , View.OnClickListener, MyDialog.DialogListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentReceiptDetailBinding
    lateinit var receiptDetailViewModel : ReceiptDetailViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_receipt_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).receiptDatabaseDao
        val receiptDetailDataSource = StoreDatabase.getInstance(application).receiptDetailDatabaseDao


       val EditReceiptId =  ReceiptDetailFragmentArgs.fromBundle(requireArguments()).receiptId

        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.ReceiptDetails)



        val viewModelFactory = ReceiptDetailViewModelFactory(EditReceiptId, dataSource,receiptDetailDataSource)
        receiptDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReceiptDetailViewModel::class.java)

        receiptDetailViewModel.latestreciept=EditReceiptId


        binding.receiptDetailViewModel= receiptDetailViewModel
        binding.setLifecycleOwner(this)






        val dataSourceSup = StoreDatabase.getInstance(application).supplierDatabaseDao
        val   supplierDetailViewModel : SupplierDetailViewModel  =SupplierDetailViewModel(0,dataSourceSup)
        val supplier = supplierDetailViewModel.getSuppliers()

        val supspinner = binding.receiptSupInput
        val languages  = resources.getStringArray(R.array.Languages)
        val empspinner = binding.receiptEmpInput
        val storspinner = binding.receiptStoreInput
        empspinner.setOnItemSelectedListener(this)
        supspinner.setOnItemSelectedListener(this)
        storspinner.setOnItemSelectedListener(this)


        val dataSourceStore = StoreDatabase.getInstance(application).storeDatabaseDao
        val   storeDetailViewModel : StoreDetailViewModel  =StoreDetailViewModel(0,dataSourceStore)


        storeDetailViewModel.getStores()
        if (storspinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,storeDetailViewModel.stores.toTypedArray())
            storspinner.adapter = adapter
        }


        val dataSourceDep = StoreDatabase.getInstance(application).departmentDatabaseDao
        val   departmentDetailViewModel : DepartmentDetailViewModel =DepartmentDetailViewModel(0,dataSourceDep)
        val departments = departmentDetailViewModel.getDepartments()
        val departmentspinner = binding.receiptDepInput
            departmentspinner.setOnItemSelectedListener(this)

        if (departmentspinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,departmentDetailViewModel.deps.toTypedArray())
            departmentspinner.adapter = adapter
        }
        binding.itemAddBtn.setOnClickListener(this)

        var receipt:Receipt = Receipt()
        binding.receipt=receipt

        val receiptdetailadapter = ReceiptDetailAdapter(ReceiptDetailListener { receiptId ->
            receiptDetailViewModel.onReceiptClicked(receiptId)

        })


            binding.receiptDetailList.adapter = receiptdetailadapter



        receiptDetailViewModel.receiptdetails?.observe(viewLifecycleOwner, Observer {
            it?.let {

         Log.i("observe", " receiptdetails")
              //  receiptdetailadapter.updateList(it as MutableList<ReceiptDetail>)
               // receiptdetailadapter.updateList(it as MutableList<ReceiptDetail>)

                receiptdetailadapter.submitList(it)

               // receiptdetailadapter.updateList(it as MutableList<ReceiptDetail>)
             //   receiptdetailadapter.filter.filter(receiptDetailViewModel.receiptEdite.receiptId.toString())
                /*
               if(receiptDetailViewModel.receiptEdite.receiptId!=0L   ) {
                    receiptdetailadapter.updateList(it as MutableList<ReceiptDetail>)
                    receiptdetailadapter.filter.filter(receiptDetailViewModel.receiptEdite.receiptId.toString())
                }else if(receiptDetailViewModel.latestreciept!=0L ){
                   receiptdetailadapter.updateList(it as MutableList<ReceiptDetail>)
                   receiptdetailadapter.filter.filter(receiptDetailViewModel.latestreciept.toString())
               }
*/


            }
        })




        binding.backBtn.setOnClickListener {

            it.findNavController().navigate(R.id.action_receiptDetailFragment_to_receiptTrackerFragment2)

        }


        receiptDetailViewModel.navigateToEditReceipt.observe(this, Observer { receipt ->
            receipt?.let {
                if(it!=null) {
                    Log.i("navigateToEditReceipt", " observe  navigateToEditReceipt"+it.toString())
                   view?.findNavController()?.navigate(
                       ReceiptDetailFragmentDirections.actionReceiptDetailFragmentSelf().setReceiptId(it)
                  )
                }

                receiptDetailViewModel.onReceiptClicked(null)
            }
        })

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
            is Department -> binding?.receipt?.receiptDepId =spinnerobj.departmentId
        }




    }


    override fun onClick(view:View) {
        val myActivity= getActivity()
        val dialogFragment = MyDialog(this)
        val bundle = Bundle()
        bundle.putBoolean("notAlertDialog", true)
        dialogFragment.arguments = bundle
        val ft = myActivity?.supportFragmentManager?.beginTransaction()
        val prev = myActivity?.supportFragmentManager?.findFragmentByTag("dialog")

        if (prev != null)
        {
            if (ft != null) {
                ft.remove(prev)
            }
        }
        if (ft != null) {
            ft.addToBackStack(null)
        }
        if (ft != null) {
            dialogFragment.show(ft, "dialog")
        }

    }
//  android:onClick="@{() -> receiptDetailViewModel.onCreateReceipt(receipt)}"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFinishEditDialog(
    receiptDetail: ReceiptDetail,
    receiptDaialogViewModel: ReceiptDialogViewModel
) {
    val application = requireNotNull(this.activity).application
  //if(receiptDetailViewModel.receiptEdite.receiptId!=0L) {
      receiptDetail.receiptId = receiptDetailViewModel.receiptKey
      Log.i("insertNet", "onFinishEditDialog"+receiptDetail.toString())
      receiptDaialogViewModel.onCreateReceiptDetailNet(receiptDetail,receiptDetailViewModel)
       receiptDetailViewModel.getReceiptDetailsNet(receiptDetail.receiptId);


   //   receiptDetailViewModel.onReceiptClicked(  receiptDetail.receiptId)


 // }else{

     //  receiptDetailViewModel.getLatestReciept()
     // receiptDetail.receiptId = receiptDetailViewModel.latestreciept
 // }


      //  receiptDetail.receiptId=   EditReceiptId
    Log.i("Insert:",receiptDetail.toString())







    }




}