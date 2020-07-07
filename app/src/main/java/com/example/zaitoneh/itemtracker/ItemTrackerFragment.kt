package com.example.zaitoneh.itemtracker

import android.os.Bundle
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
import com.example.zaitoneh.database.Item

import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentItemTrackerBinding




class ItemTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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



        /******************************************/
     val adapter = ItemAdapter(ItemListener { itemId ->
            itemTrackerViewModel.onItemClicked(itemId)
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })




        itemTrackerViewModel.navigateToEditItem.observe(this, Observer { item ->
            item?.let {

                view?.findNavController()?.navigate(ItemTrackerFragmentDirections.actionItemTrackerFragmentToItemDetailFragment().setItemId(it))
            }
        })


        binding.itemList.adapter = adapter


        itemTrackerViewModel.items.observe(viewLifecycleOwner, Observer {
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


}



