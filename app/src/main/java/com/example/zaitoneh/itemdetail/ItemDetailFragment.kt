package com.example.zaitoneh.itemdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.loader.app.LoaderManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentItemDetailBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import android.content.res.Resources
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class ItemDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title =  context?.resources?.getString(R.string.itemDetail)
        // Inflate the layout for this fragment
        val binding: FragmentItemDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_item_detail, container, false)
        var item:Item=Item()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).itemDatabaseDao
        val args = ItemDetailFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = ItemDetailViewModelFactory(args.itemId,dataSource)
        val itemDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ItemDetailViewModel::class.java)

        /*if (args.itemId==0L) binding.saveBtn.text=this.context?.resources?.getString(R.string.save)
        else{
            binding.saveBtn.text=this.context?.resources?.getString(R.string.update)

        }*/


        binding.itemDetailViewModel = itemDetailViewModel
        binding.setLifecycleOwner(this)


        itemDetailViewModel.updateItemToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "This item is updated",Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.navigate(R.id.action_itemDetailFragment_to_itemTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error This item is not  updated",Toast.LENGTH_LONG
                    ).show()
            }
        })

        itemDetailViewModel.deleteItemFromDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "The item has been deleted",Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.navigate(R.id.action_itemDetailFragment_to_itemTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error, The item wasn't deleted",Toast.LENGTH_LONG
                    ).show()
            }
        })
        itemDetailViewModel.saveItemToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                binding.item=Item()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "This item is already exist",Toast.LENGTH_LONG
                    ).show()
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

        binding.backBtn.setOnClickListener {

            it.findNavController().navigate(R.id.action_itemDetailFragment_to_itemTrackerFragment)
        }

      binding.item=Item()



      //  binding.item=  itemDetailViewModel.getItem().value as  Item
        return binding.root
    }




    override fun onDestroy() {
        super.onDestroy()
        Log.i("itemdetails" ," onDestroy" )
    }




}

