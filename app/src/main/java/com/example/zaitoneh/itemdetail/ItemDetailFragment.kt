package com.example.zaitoneh.itemdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.zaitoneh.R
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

        return binding.root
    }

}
