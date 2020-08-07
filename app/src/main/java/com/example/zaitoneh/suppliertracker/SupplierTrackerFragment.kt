package com.example.zaitoneh.suppliertracker

import android.os.Bundle
import android.provider.ContactsContract
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
import androidx.navigation.fragment.findNavController
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.database.Supplier
import com.example.zaitoneh.databinding.FragmentSupplierTrackerBinding
import com.example.zaitoneh.storetracker.StoreTrackerViewModel
import com.example.zaitoneh.storetracker.StoreTrackerViewModelFactory


class SupplierTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSupplierTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_supplier_tracker, container, false)
        binding.addSupplierButton.setOnClickListener { view: View ->
            view.findNavController().navigate(SupplierTrackerFragmentDirections.actionSupplierTrackerFragmentToSupplierDetailFragment())
        }

        /****************************************************************************/
        val application = requireNotNull(this.activity).application

        val dataSource = StoreDatabase.getInstance(application).supplierDatabaseDao

        val viewModelFactory = SupplierTrackerViewModelFactory(dataSource, application)

        val supplierTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(SupplierTrackerViewModel::class.java)

        //binding.storeTrackerViewModel = storeTrackerViewModel

        binding.setLifecycleOwner(this)


        /******************************************/
        val adapter = SupplierAdapter(SupplierListener { supId ->
            supplierTrackerViewModel.onSupplierClicked(supId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })

        supplierTrackerViewModel.suppliers.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Supplier>)
                adapter.submitList(it)
            }
        })
        supplierTrackerViewModel.navigateToEditSupplier.observe(this, Observer { supplier ->
            supplier?.let {
                if(it!=0L) {
                    view?.findNavController()?.navigate(
                        SupplierTrackerFragmentDirections.actionSupplierTrackerFragmentToSupplierDetailFragment()
                            .setSupplierId(it)
                    )
                    supplierTrackerViewModel.onSupplierClicked(0L)
                }

            }
        })


        binding.supplierList.adapter = adapter


        binding.addSupplierButton.setOnClickListener {

            it.findNavController().navigate(SupplierTrackerFragmentDirections.actionSupplierTrackerFragmentToSupplierDetailFragment())

        }

        val supplier_search= binding.supplierSearch
        supplier_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

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



