package com.example.zaitoneh.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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

                itemDetailViewModel.setSaveItemToDataBase()
            }
        })



        return binding.root
    }

}
