package com.example.noteappviaapi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.ForgotPasswordRequestModel
import com.example.noteappviaapi.Model.OnetimelogininResponse
import com.example.noteappviaapi.Model.addNoteResponseModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentRequestLoginLinkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding:FragmentRequestLoginLinkBinding
class RequestLoginLink : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_login_link, container, false)

        binding.EnterEmailforForgotPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
               hideKeyboard(v);
            }
        }
        binding.BackToLogin.setOnClickListener({
            it.findNavController().navigate(R.id.action_requestLoginLink_to_loginIn)
        })

        binding.SendLoginLinkBtn.setOnClickListener({
            val email = binding.EnterEmailforForgotPassword.text
            if (email.isEmpty()) {
                Toast.makeText(
                    it.context,
                    "Please fill email field.",
                    Toast.LENGTH_LONG
                ).show()
            }else{
            val APIval = MainActivity().APIClient().create(APIService::class.java)
            val call = APIval.Onetimeloginlink(ForgotPasswordRequestModel(email.toString()))

            call.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(it.context, "Login Link send by email", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            it.context,
                            "Invalid Request",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                    Toast.makeText(it.context, t.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }

        })
        return binding.root
    }
    fun hideKeyboard(view: View) {
        val inputMethodManager =activity!!. getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
