package com.example.noteappviaapi.Fragments
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.DefaultUserResponse
import com.example.noteappviaapi.Model.MyErrorMessageModel
import com.example.noteappviaapi.Model.userModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentSignUpBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: FragmentSignUpBinding

class SignUp : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)


        binding.Register.setOnClickListener {

            val APIval = MainActivity().APIClient().create(APIService::class.java)
            val EnterUsername = binding.editTextUsername.text.toString()
            val EnterPassword = binding.passwordEditText.text.toString()
            val ConfirmPassword = binding.ConfirmpasswordEditText.text.toString()

            if(EnterUsername.isEmpty() || EnterPassword.isEmpty() || ConfirmPassword.isEmpty()){
                Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
            }else if(EnterUsername.length <4){
                Toast.makeText(activity, "Username must be longer or equal to 4 characters.", Toast.LENGTH_LONG).show()
            }else if(EnterPassword.length <6){
                Toast.makeText(activity, "Password must be longer or equal to 6 characters.", Toast.LENGTH_LONG).show()
            }else if(EnterPassword != ConfirmPassword){
                Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_LONG).show()
            }else{
                val usermodel = userModel(EnterUsername,EnterPassword );
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

        binding.Login.setOnClickListener({
           it.findNavController().navigate(R.id.action_signUp_to_loginIn)
        })
    }
   return binding.root
    }
}
