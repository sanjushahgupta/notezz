package com.example.noteappviaapi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.noteappviaapi.R
import com.example.noteappviaapi.SharedPreference


private lateinit var binding: com.example.noteappviaapi.databinding.FragmentStartingpageBinding
class Startingpage : Fragment() {
    var  sharedPreference : SharedPreference? =  null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_startingpage,container,false)
        sharedPreference  = context?.let { SharedPreference(it) }

        val  str_login_status =  sharedPreference !!.getPreferenceString( "login_status" )
        if  (!str_login_status?.isEmpty()!!){
           // Toast.makeText(activity,"str_login_status is " + str_login_status,Toast.LENGTH_LONG).show()
           val bundle = bundleOf("SavedToken" to str_login_status)
            findNavController().navigate(R.id.show,bundle)
        }
        binding.GoTOSignUpPage.setOnClickListener({
            it.findNavController().navigate(R.id.action_startingpage_to_signUp)
        })

        binding.GoToLogInPage.setOnClickListener({
            it.findNavController().navigate(R.id.action_startingpage_to_loginIn)
        })
        return binding.root

    }

}
