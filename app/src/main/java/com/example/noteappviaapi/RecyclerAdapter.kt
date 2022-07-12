package com.example.noteappviaapi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviaapi.Fragments.Show
import com.example.noteappviaapi.Model.addNoteResponseModel
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecyclerAdapter(
    private val mylist: List<addNoteResponseModel>,
    val Token: String,
    val Created: String,
    val Updated: String,
    val AccountUsername: String
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        var itemDescription: TextView
        var deletebtn: Button




        init {
            itemTitle = itemView.findViewById(R.id.titleTextview)
            itemDescription = itemView.findViewById(R.id.descriptionTextview)
            deletebtn = itemView.findViewById(R.id.deleteBtn)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)


        return ViewHolder(v)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,position: Int) {

  holder.itemTitle.text =  mylist[position].title.toString()
        holder.itemDescription.text =  mylist[position].body.toString()
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("NoteTitle", mylist[position].title.toString())
            bundle.putString("Notebody", mylist[position].body.toString())
            bundle.putInt("id", mylist[position].id.toInt())
            bundle.putInt("UserId", mylist[position].userId.toInt())
            bundle.putString("SavedToken", Token)
            bundle.putString("Created", Created)
            bundle.putString("Updated", Updated)
            bundle.putString("AccountUsername", AccountUsername)

            it.findNavController().navigate(R.id.action_show_to_addEdit, bundle)
        }


        holder.deletebtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it.context)

            builder.setTitle("Delete Confirmation")
            builder.setMessage("Are you sure? This note will be gone for good!")

            builder.setPositiveButton(
                "Delete",
                DialogInterface.OnClickListener { dialog, which ->

                    val APIval = MainActivity().APIClient().create(APIService::class.java)
                    APIval.DeleteNote("Bearer $Token", mylist[position].id)
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                            ) {

                                if (response.isSuccessful) {
                                   // notifyDataSetChanged()

                                    Toast.makeText(
                                        it.context, response.code().toString(),
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        it.context,
                                        response.code().toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {

                                Toast.makeText(it.context, t.toString(), Toast.LENGTH_LONG).show()
                            }
                        })
                })
            builder.setNegativeButton( "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    })
            val alert: AlertDialog = builder.create()
            alert.show()

        }

    }

        override fun getItemCount(): Int {

        return mylist.size
    }



}
