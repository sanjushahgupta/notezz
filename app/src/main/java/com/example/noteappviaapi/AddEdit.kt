package com.example.noteappviaapi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.databinding.FragmentAddEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding:FragmentAddEditBinding

class AddEdit : Fragment() {
        private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
               val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(sharedPrefFile,
                   Context.MODE_PRIVATE)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.notezz.com")
            .build()
               val passedTokenFromLogin = arguments?.getString("SavedToken")




        binding.AddButton.setOnClickListener {
              val APIval = retrofitBuilder.create(APIService::class.java)
                 val call = APIval.addNote("Bearer $passedTokenFromLogin",noteModel("bodySTRING", "ghv","Active") )

               call.enqueue(object : Callback<addNoteResponseModel>{
                   override fun onResponse(
                       call: Call<addNoteResponseModel>,
                       response: Response<addNoteResponseModel>
                   ) {
                          if(response.isSuccessful){
                              Toast.makeText(activity,"note added successfully of userid" +response.body()!!.userId.toString(), Toast.LENGTH_LONG).show()

                              val UserToken = passedTokenFromLogin
                               val bundles = bundleOf("UserToken" to UserToken )

                              it.findNavController().navigate(R.id.action_addEdit_to_show,bundles)

                          }else{
                            Toast.makeText(activity, response.code().toString() , Toast.LENGTH_LONG).show()
                          }

                       }
                   override fun onFailure(call: Call<addNoteResponseModel>, t: Throwable) {
                          Toast.makeText(activity,"error"+ t.toString(), Toast.LENGTH_LONG).show()
                   }

               })
           }

        return binding.root
    }







}
