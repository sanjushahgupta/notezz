package com.example.noteappviaapi.Fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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
import com.example.noteappviaapi.SharedPreference
import com.example.noteappviaapi.databinding.FragmentLoginInBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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


        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        binding.editTextPassword.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v);
            }
        })
        binding.editTextUsername.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v);
            }
        })

        binding.ForgotPassword.setOnClickListener({
            it.findNavController().navigate(R.id.action_loginIn_to_requestLoginLink)
            })

    binding.LoginButtonBtn.setOnClickListener {
        val APIval = MainActivity().APIClient().create(APIService::class.java)
        val EnterUsername =/*binding.editTextUsername.text.toString()*/"sanju"
        val EnterPassword =/* binding.editTextPassword.text.toString()*/"mankind"

        if (EnterUsername.isEmpty() || EnterPassword.isEmpty()) {
            Toast.makeText(activity, "Please fill all fields.", Toast.LENGTH_LONG).show()
        } else {

            val usermodel = userModel(EnterUsername, EnterPassword);
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
                    if (response.isSuccessful) {
                        val SavedToken = response.body()!!.token
                       // val AccountUsername = EnterUsername.toString()

                        SharedPreference(requireContext()).save_String("login_status" , response.body()!!.token.toString())
                        val bundle = bundleOf("SavedToken" to SavedToken)


                        it.findNavController().navigate(R.id.action_loginIn_to_show, bundle)

                    } else {
                        val gson = Gson()
                        val message = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            MyErrorMessageModel::class.java
                        )

                        Toast.makeText(
                            activity,
                            message.message,
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            })
        }
    }
    return binding.root
}

    fun hideKeyboard(view: View) {
        val inputMethodManager =activity!!. getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
