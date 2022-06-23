package com.example.noteappviaapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.noteappviaapi.databinding.FragmentAddEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding:FragmentAddEditBinding

class AddEdit : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.notezz.com")
            .build()
               val passedToken = arguments?.getString("SavedToken")
             binding.AddButton.setOnClickListener {
               val APIval = retrofitBuilder.create(APIService::class.java)
                 val call = APIval.addNote("Bearer $passedToken",noteModel("bodySTRING", "ghv","Active") )

               call.enqueue(object : Callback<noteModel>{
                   override fun onResponse(
                       call: Call<noteModel>,
                       response: Response<noteModel>
                   ) {
                          if(response.isSuccessful){
                              Toast.makeText(activity,"Notes added successfully", Toast.LENGTH_LONG).show()

                   }else{
                            Toast.makeText(activity, response.code().toString() , Toast.LENGTH_LONG).show()
                          }

                       }
                   override fun onFailure(call: Call<noteModel>, t: Throwable) {
                          Toast.makeText(activity,"error"+ t.toString(), Toast.LENGTH_LONG).show()
                   }

               })
           }

        return binding.root
    }







}
