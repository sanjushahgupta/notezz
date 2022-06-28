package com.example.noteappviaapi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.databinding.FragmentDetailsBinding

private lateinit var binding: FragmentDetailsBinding


class details : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        binding.updatebutton.setOnClickListener({
            view?.findNavController()?.navigate(R.id.action_details_to_addEdit)
        })

      // val SavedId = arguments?.getString("SavedId")!!
        // val SavedTitle = arguments?.getString("SavedTitle")!!
     //   val Savedbody = arguments?.getString("Savedbody")!!

        return binding.root
    }
}
