package com.example.zaitoneh.receipttracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.zaitoneh.database.Receipt

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentReceiptTrackerBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ReceiptTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentReceiptTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_receipt_tracker, container, false)
        binding.addRecieptButton.setOnClickListener { view: View ->
            view.findNavController().navigate(ReceiptTrackerFragmentDirections.actionReceiptTrackerFragment2ToReceiptDetailFragment())
        }



        /****************************************************************************/
        val application = requireNotNull(this.activity).application
        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.ReceiptTracker)

        val dataSource = StoreDatabase.getInstance(application).receiptDatabaseDao

        val viewModelFactory = ReceiptTrackerViewModelFactory(dataSource, application)

        val receiptTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ReceiptTrackerViewModel::class.java)

        //binding.receiptTrackerViewModel = receiptTrackerViewModel

        binding.setLifecycleOwner(this)



        /******************************************/
        val adapter = ReceiptAdapter(ReceiptListener { receiptId ->
            receiptTrackerViewModel.onReceiptClicked(receiptId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




        receiptTrackerViewModel.navigateToEditReceipt.observe(this, Observer { receipt ->
            receipt?.let {
                if(it!=null) {
                    view?.findNavController()?.navigate(
                        ReceiptTrackerFragmentDirections.actionReceiptTrackerFragment2ToReceiptDetailFragment()
                            .setReceiptId(it)
                    )
                }

                receiptTrackerViewModel.onReceiptClicked(null)
            }
        })


        binding.receiptDetailList.adapter = adapter


        receiptTrackerViewModel.list.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Receipt>)
                adapter.submitList(it)


            }
        })



        val receipt_search= binding.recieptSearch
        receipt_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)
                return false

            }

        })





        val   bottomAppBar = getActivity()?.findViewById<BottomAppBar>(R.id.bottomAppBar)

        if (bottomAppBar != null) {
            bottomAppBar.visibility=  View.VISIBLE
        }

        val   floatingActionButton = getActivity()?.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        if (floatingActionButton != null) {
            floatingActionButton.visibility=  View.VISIBLE
        }
        return binding.root
    }


}



