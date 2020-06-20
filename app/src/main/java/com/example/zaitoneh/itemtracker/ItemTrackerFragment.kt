package com.example.zaitoneh.itemtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zaitoneh.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController

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

        binding.addButton.setOnClickListener (
            //view.findNavController().navigate(R.id.action_itemTrackerFragment_to_itemDetailFragment)
            Navigation.createNavigateOnClickListener(R.id.action_itemTrackerFragment_to_itemDetailFragment))

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
    /* val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })*/



        return binding.root
    }


}



