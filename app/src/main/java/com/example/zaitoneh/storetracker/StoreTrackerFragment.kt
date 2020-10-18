package com.example.zaitoneh.storetracker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentStoreTrackerBinding


class StoreTrackerFragment : Fragment(){
    internal lateinit var btnEmbedDialogFragment: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.StoreTracker)
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

                if(it!=null) {

                    view?.findNavController()?.navigate(
                        StoreTrackerFragmentDirections.actionStoreTrackerFragmentToStoreDetailFragment().setStoreId(it))

                    storeTrackerViewModel.onStoreClicked(null)



                }


            }
        })


        binding.storeList.adapter = adapter




        storeTrackerViewModel.list.observe(viewLifecycleOwner, Observer {
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
                Log.i("filter",newText);
                adapter.filter.filter(newText)
                return false

            }

        })


        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }




}



