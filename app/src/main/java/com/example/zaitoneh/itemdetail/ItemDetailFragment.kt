package com.example.zaitoneh.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentItemDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class ItemDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentItemDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_item_detail, container, false)
        var item:Item=Item()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).itemDatabaseDao
        val viewModelFactory = ItemDetailViewModelFactory(item.itemId,dataSource)
        val itemDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ItemDetailViewModel::class.java)
        binding.itemDetailViewModel = itemDetailViewModel
        binding.setLifecycleOwner(this)
        binding.item=Item()

        itemDetailViewModel.saveItemToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                binding.item=Item()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "This item is already exist",Toast.LENGTH_LONG
                    ).show()
                //itemDetailViewModel.setSaveItemToDataBase()
            }
        })

        itemDetailViewModel.itemValidation.observe(this, Observer {
            if (it == false) { // Observed state is true.
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Please Fill all fields",Toast.LENGTH_LONG
                    ).show()
                itemDetailViewModel.setSaveItemToDataBase()
            }
        })

        binding.backBtn.setOnClickListener (
            //view.findNavController().navigate(R.id.action_itemTrackerFragment_to_itemDetailFragment)
            Navigation.createNavigateOnClickListener(R.id.action_itemDetailFragment_to_itemTrackerFragment))


        return binding.root
    }

}
