package com.example.noteappviaapi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.R


private lateinit var binding: com.example.noteappviaapi.databinding.FragmentStartingpageBinding
class Startingpage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_startingpage,container,false)
        binding.GoTOSignUpPage.setOnClickListener({
            it.findNavController().navigate(R.id.action_startingpage_to_signUp)
        })

        binding.GoToLogInPage.setOnClickListener({
            it.findNavController().navigate(R.id.action_startingpage_to_loginIn)
        })
        return binding.root

    }

}
