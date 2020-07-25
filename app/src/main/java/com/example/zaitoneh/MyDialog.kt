    package com.example.zaitoneh

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.zaitoneh.database.*
import com.example.zaitoneh.databinding.FragmentMyDialogBinding

import com.example.zaitoneh.departmentdetail.DepartmentDetailViewModel
import com.example.zaitoneh.itemdetail.ItemDetailViewModel
import com.example.zaitoneh.receipt.ReceiptDetailFragment
import com.example.zaitoneh.receipttracker.ReceiptTrackerFragment
import com.example.zaitoneh.storetracker.StoreTrackerFragment

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyDialog(receiptDetailFragment: ReceiptDetailFragment) : DialogFragment() {
    lateinit var receiptFragment:ReceiptDetailFragment
    lateinit var binding: FragmentMyDialogBinding

    lateinit var   spinnerobj : Item
    init {
        receiptFragment=receiptDetailFragment
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null)
        {
            if (arguments?.getBoolean("notAlertDialog")!!)
            {
                return super.onCreateDialog(savedInstanceState)
            }
        }
        val builder = AlertDialog.Builder(activity)

        builder.setPositiveButton("Cool", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                dismiss()
            }
        })
        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                dismiss()
            }
        })
        return builder.create()
    }
   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_dialog, container, false)

        val empspinner = binding.receiptEmpInp*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_dialog, container, false
        )
        val application = requireNotNull(this.activity).application

// add searchable spinner  for all items
        val dataSourceItem = StoreDatabase.getInstance(application).itemDatabaseDao
        val   itemDetailViewModel : ItemDetailViewModel =
            ItemDetailViewModel(0,dataSourceItem)

        val items = itemDetailViewModel.getItems()


          val spinnerItem =      binding.spinnerItem
      //    spinnerItem.setOnItemSelectedListener(this)


        if (spinnerItem != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,itemDetailViewModel.items.toTypedArray())
            spinnerItem.adapter = adapter
        }





        //***************************************************************
         binding.saveBtk.setOnClickListener {



            }

        return binding.root
    }
    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtk.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {

                val dialogListener = receiptFragment as DialogListener
                var receiptDetail :ReceiptDetail = ReceiptDetail()

                receiptDetail.amount=   binding.inputAmount.text.toString().toFloat()
                receiptDetail.itemPrice=   binding.inputPrice.text.toString().toFloat()
                receiptDetail.receiptId=1
                receiptDetail.itemId=   (binding.spinnerItem.selectedItem as Item).itemId




                dialogListener.onFinishEditDialog(receiptDetail)
                dismiss()

            }
        })


    }




    override fun onResume() {
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Hey", "onCreate")
        var  setFullScreen = false
        if (arguments != null) {
            setFullScreen = requireNotNull(arguments?.getBoolean("fullScreen"))
        }
        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    interface DialogListener {
        fun onFinishEditDialog(receiptDetail: ReceiptDetail)
    }


}