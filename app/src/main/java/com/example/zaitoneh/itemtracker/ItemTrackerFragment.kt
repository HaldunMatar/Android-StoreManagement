package com.example.zaitoneh.itemtracker

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.zaitoneh.database.Item

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentItemTrackerBinding
import com.example.zaitoneh.storetracker.StoreTrackerFragmentDirections


class ItemTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.items_Tracker)

        // Inflate the layout for this fragment
        val binding: FragmentItemTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_item_tracker, container, false)
           binding.addButton.setOnClickListener { view: View ->
          view.findNavController().navigate(ItemTrackerFragmentDirections.actionItemTrackerFragmentToItemDetailFragment()


          )

        }



        /****************************************************************************/
       val application = requireNotNull(this.activity).application

      val dataSource = StoreDatabase.getInstance(application).itemDatabaseDao

      val viewModelFactory = ItemTrackerViewModelFactory(dataSource, application)

        val itemTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ItemTrackerViewModel::class.java)

        binding.itemTrackerViewModel = itemTrackerViewModel

      binding.setLifecycleOwner(this)

        setHasOptionsMenu(true)

        /******************************************/
     val adapter = ItemAdapter(ItemListener { itemId ->
            itemTrackerViewModel.onItemClicked(itemId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




        itemTrackerViewModel.navigateToEditItem.observe(this, Observer { item ->
            item?.let {
                if(it!=null) {
                    view?.findNavController()?.navigate(
                       ItemTrackerFragmentDirections.actionItemTrackerFragmentToItemDetailFragment().setItemId(it)

                    )
                    itemTrackerViewModel.onItemClicked(null)
                }
            }
        })


        binding.itemList.adapter = adapter


   /* itemTrackerViewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Item>)
                adapter.submitList(it)


            }
        })*/

     itemTrackerViewModel.properties.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateList(it as MutableList<Item>)
                adapter.submitList(it)
            }

        })

       val item_search= binding.itemSearch
        item_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }






}



