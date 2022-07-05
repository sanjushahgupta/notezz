package com.example.noteappviaapi.Fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.Model.DefaultUserResponse
import com.example.noteappviaapi.Model.userModel
import com.example.noteappviaapi.Model.MyErrorMessageModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentSignUpBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: FragmentSignUpBinding

class SignUp : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.notezz.com")
            .build()

        binding.Register.setOnClickListener {
            val APIval = retrofitBuilder.create(APIService::class.java)
            val usermodel = userModel(binding.editTextUsername.text.toString(), binding.passwordEditText.text.toString());
             val call = APIval.createUser(usermodel)
            call.enqueue(object : Callback<DefaultUserResponse> {
                override fun onFailure(call: Call<DefaultUserResponse>, t: Throwable) {
                    Log.d("msggo", t.toString());
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<DefaultUserResponse>,
                    response: Response<DefaultUserResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                           activity,
                            response.code().toString(),
                            Toast.LENGTH_LONG
                        ).show()

                    }else{
                        val message =  Gson().fromJson(
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
