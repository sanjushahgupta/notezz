package com.example.noteappviaapi.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.*
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentAccountBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private lateinit var binding:FragmentAccountBinding
class Account : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.UpdateUsernameTxt.setText(arguments!!.getString("AccountUsername"))
        var SavedToken: String? = arguments!!.getString("SavedToken")
        binding.UpdateAccount.setOnClickListener {
            val APIval = MainActivity().APIClient().create(APIService::class.java)
            val UpdateUsernameTxt = binding.UpdateUsernameTxt.text.toString()
            val UpdatePasswordtxt = binding.UpdatePasswordtxt.text.toString()
            val UpdateEmailTxt = binding.UpdateEmailTxt.text.toString()

            if(UpdateUsernameTxt.isEmpty())
            {
                Toast.makeText(
                    activity,
                    "Username Field is empty.",
                    Toast.LENGTH_LONG
                ).show()
            }else if (!UpdatePasswordtxt.equals(binding.UpdateConfirmNewPassword.text.toString())) {
                    Toast.makeText(
                        activity,
                        "Passwords do not match.",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (!UpdatePasswordtxt .isEmpty()) {
                    if(UpdatePasswordtxt.length < 6)
                    {
                        Toast.makeText(
                            activity,
                            "Passwords length must be higher than 5 characters.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                val call = APIval.UpdateUserAccount(
                    "Bearer $SavedToken",
                    UpdateAccountRequestModel(
                        UpdateUsernameTxt,
                        UpdatePasswordtxt,
                        UpdateEmailTxt
                    )
                )

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Account Updated",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {
                            Toast.makeText(
                                activity,
                                "error:" + response.code().toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            activity,
                            "error is" + t.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            } else {

                val call = APIval.UpdateUserAccountusernamenadpassword(
                    "Bearer $SavedToken",
                    UpdateEmail (
                        UpdateUsernameTxt,
                        UpdateEmailTxt
                    )
                )
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.isSuccessful){
                            Toast.makeText(
                                activity,
                                "Account Updated",
                                Toast.LENGTH_LONG
                            ).show()
                        }else{
                            Toast.makeText(
                                activity,
                                "error is" + response.code().toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                                activity,
                        "error:" + t.toString(),
                        Toast.LENGTH_LONG
                        ).show()
                    }

                })

            }

        }




        binding.DeleteAccount.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it.context)

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure?")

            builder.setPositiveButton(
                "Yes, delete my account",
                DialogInterface.OnClickListener { dialog, which ->
                    val APIval = MainActivity().APIClient().create(APIService::class.java)
                    val call = APIval.DeleteAccount(
                        "Bearer $SavedToken",

                        )
                    call.enqueue(object : Callback<Void>{
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    activity,
                                    "Account deleted successfully.",
                                    Toast.LENGTH_LONG
                                ).show()
                                it.findNavController().navigate(R.id.startingpage)
                            }else{
                                Toast.makeText(
                                    activity,
                                    "Sorry, Unable to delete account. Try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(
                                activity,
                                "error"+ t.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })


                })

            builder.setNegativeButton(
                "No,I will give another try.",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

            val alert: AlertDialog = builder.create()
            alert.show()
        }
        return binding.root
    }

}
