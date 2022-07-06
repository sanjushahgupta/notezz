package com.example.noteappviaapi.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.DefaultUserResponse
import com.example.noteappviaapi.Model.MyErrorMessageModel
import com.example.noteappviaapi.Model.userModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentLoginInBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private lateinit var binding: FragmentLoginInBinding
class LoginIn : Fragment(){
    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
   
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_in, container, false)
    

        binding.RegisterBtn.setOnClickListener({
            it.findNavController().navigate(R.id.action_loginIn_to_signUp)
        })

    binding.LoginButtonBtn.setOnClickListener {
        val APIval =MainActivity().APIClient().create(APIService::class.java)
        val usermodel = userModel(/*binding.editTextUsername.text.toString()*/"sanju", /*binding.editTextPassword .text.toString()*/"2rxbjjbd");
        val call = APIval.login(usermodel)
        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        call.enqueue(object : Callback<DefaultUserResponse> {
            override fun onFailure(call: Call<DefaultUserResponse>, t: Throwable) {
                Log.d("msggo", t.toString());
                Toast.makeText(activity, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<DefaultUserResponse>,
                response: Response<DefaultUserResponse>
            ) {
                if (response.isSuccessful){
                    val SavedToken = response.body()!!.token
                    val bundle = bundleOf("SavedToken" to SavedToken )

                    it.findNavController().navigate(R.id.action_loginIn_to_show, bundle)

                }else{
                    val gson = Gson()
                    val message = gson.fromJson(
                        response.errorBody()!!.charStream(),
                        MyErrorMessageModel::class.java)

                    Toast.makeText(
                        activity,
                        message.message,
                        Toast.LENGTH_LONG
                    ).show();
                }
            }
        })
    }
    return binding.root
}



}
