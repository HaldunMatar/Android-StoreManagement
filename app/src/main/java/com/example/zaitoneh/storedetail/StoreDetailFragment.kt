package com.example.zaitoneh.storedetail

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.zaitoneh.R
import com.example.zaitoneh.database.Item
import com.example.zaitoneh.database.Store
import com.example.zaitoneh.database.StoreDatabaseDao
import com.example.zaitoneh.database.StoreDatabase
import com.example.zaitoneh.databinding.FragmentStoreDetailBinding

//import com.example.zaitoneh.storedetail.StoreDetailFragmentArgs
import com.example.zaitoneh.storedetail.StoreDetailViewModel
import com.example.zaitoneh.storedetail.StoreDetailViewModelFactory
import kotlinx.coroutines.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStoreDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_store_detail, container, false
        )
        var store: Store = Store()
        val application = requireNotNull(this.activity).application
        val dataSource = StoreDatabase.getInstance(application).storeDatabaseDao
      val args = StoreDetailFragmentArgs.fromBundle(requireArguments())

        if (args.storeId==0L) binding.saveStoreButton.text=this.context?.resources?.getString(R.string.save)
        else{
            binding.saveStoreButton.text=this.context?.resources?.getString(R.string.update)
        }

        val viewModelFactory = StoreDetailViewModelFactory(args.storeId, dataSource)

        val storeDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(StoreDetailViewModel::class.java)


        binding.storeDetailViewModel = storeDetailViewModel
        binding.setLifecycleOwner(this)

        binding.store=Store()

        //**************************************


        storeDetailViewModel.updateStoreToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "This store is updated", Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.navigate(R.id.action_storeDetailFragment_to_storeTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error This store is not  updated",
                        Toast.LENGTH_LONG
                    ).show()

            }
        })

        storeDetailViewModel.deleteStoreFromDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(activity!!.applicationContext, "The store has been deleted", Toast.LENGTH_LONG
                ).show()
               view?.findNavController()?.navigate(R.id.action_storeDetailFragment_to_storeTrackerFragment)
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Error, The store wasn't deleted",
                        Toast.LENGTH_LONG
                    ).show()
            }
        })








        storeDetailViewModel.saveStoreToDataBase.observe(this, Observer {
            if (it == true) { // Observed state is true.
                binding.store= Store()
            }
            else{
                val toast =
                    Toast.makeText(activity!!.applicationContext, "This store is already exist", Toast.LENGTH_LONG
                    ).show()

            }
        })

        storeDetailViewModel.storeValidation.observe(this, Observer {
            if (it == false) { // Observed state is true.
                val toast =
                    Toast.makeText(activity!!.applicationContext, "Please Fill all fields", Toast.LENGTH_LONG
                    ).show()
                storeDetailViewModel.setSaveStoreToDataBase()
            }
        })




        binding.backStoreButton.setOnClickListener {

           it.findNavController().navigate(R.id.action_storeDetailFragment_to_storeTrackerFragment)

        }






        binding.store=Store()




       /* binding.saveStoreButton.setOnClickListener {

           storeDetailViewModel.onCreateStore(Store(11,"GOP1","01541","adress1"))
        }*/









        //******************************************





        return binding.root
    }










}

  