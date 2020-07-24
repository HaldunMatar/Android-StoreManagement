package com.example.zaitoneh.storetracker

import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zaitoneh.MyDialog
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.storetracker.StoreTrackerViewModel
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentStoreTrackerBinding
import kotlinx.android.synthetic.main.fragment_store_tracker.*


class StoreTrackerFragment : Fragment(){
    internal lateinit var btnEmbedDialogFragment: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStoreTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_store_tracker, container, false)
           binding.addStoreButton.setOnClickListener { view: View ->
          //view.findNavController().navigate(StoreTrackerFragmentDirections.actionStoreTrackerFragmentToStoreDetailFragment().setStoreId(it))
        }


        /****************************************************************************/
       val application = requireNotNull(this.activity).application

      val dataSource = StoreDatabase.getInstance(application).storeDatabaseDao

      val viewModelFactory = StoreTrackerViewModelFactory(dataSource, application)

        val storeTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(StoreTrackerViewModel::class.java)

        //binding.storeTrackerViewModel = storeTrackerViewModel

      binding.setLifecycleOwner(this)



        /******************************************/
     val adapter = StoreAdapter(StoreListener { storeId ->
            storeTrackerViewModel.onStoreClicked(storeId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




        storeTrackerViewModel.navigateToEditStore.observe(this, Observer { store ->
            store?.let {

                view?.findNavController()?.navigate(StoreTrackerFragmentDirections.actionStoreTrackerFragmentToStoreDetailFragment().setStoreId(it))
            }
        })


        binding.storeList.adapter = adapter


        storeTrackerViewModel.stores.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Store>)
                adapter.submitList(it)
            }
        })

        binding.addStoreButton.setOnClickListener {

            it.findNavController().navigate(StoreTrackerFragmentDirections.actionStoreTrackerFragmentToStoreDetailFragment())

        }

        val store_search= binding.storeSearch
        store_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

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



