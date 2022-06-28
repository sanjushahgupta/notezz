package com.example.noteappviaapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviaapi.databinding.FragmentShowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding:FragmentShowBinding
class Show : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.notezz.com")
            .build()
         val passedTokenFromAdd = arguments?.getString("UserToken")

        val APIval = retrofitBuilder.create(APIService::class.java)
       val call = APIval.ShowNote("Bearer $passedTokenFromAdd" )

       call.enqueue(object:Callback<List<addNoteResponseModel>>{
            override fun onResponse(call: Call<List<addNoteResponseModel>>, response: Response<List<addNoteResponseModel>>) {

                if (response.isSuccessful){
                   val movieList = response.body();
                    binding.recyclerView.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = response.body()?.let { RecyclerAdapter(it) }

                    }
                }else{
                    Toast.makeText(
                        activity,
                        response.code().toString(),
                        Toast.LENGTH_LONG
                    ).show();
                }
            }

            override fun onFailure(call: Call<List<addNoteResponseModel>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return binding.root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }
}
