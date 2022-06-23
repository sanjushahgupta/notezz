package com.example.noteappviaapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.databinding.FragmentLoginInBinding
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: FragmentLoginInBinding
class LoginIn : Fragment(){
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_in, container, false)
    val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.notezz.com")
        .build()

    binding.LoginButton.setOnClickListener {
        val APIval = retrofitBuilder.create(APIService::class.java)
        val usermodel = userModel(binding.usernameEditTextLogin.text.toString(), binding.passwordEditTextLogin.text.toString());
        val call = APIval.login(usermodel)
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
                    it.findNavController().navigate(R.id.action_loginIn_to_addEdit)
                    //TODO save token in shared reference

                }else{
                    val gson = Gson()
                    val message = gson.fromJson(
                        response.errorBody()!!.charStream(),
                        MyErrorMessage::class.java)

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