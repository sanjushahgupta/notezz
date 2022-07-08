package com.example.noteappviaapi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentAboutUsBinding

private lateinit var binding: FragmentAboutUsBinding
class AboutUs : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false)

        return binding.root
    }

}
