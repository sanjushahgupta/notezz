package com.example.noteappviaapi

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviaapi.Model.MyErrorMessageModel
import com.example.noteappviaapi.Model.StatusModel
import com.example.noteappviaapi.Model.addNoteResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecyclerAdapter(private val mylist: List<addNoteResponseModel>,val Token: String, val Created: String, val Updated: String) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView
        var itemDescription: TextView
        var deletebtn : Button

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = mylist[position].title.toString()
        holder.itemDescription.text = mylist[position].body.toString()


        holder.itemView.setOnClickListener {
           // val SavedId =  mylist[position].title.toString()
           // val SavedTitle =  mylist[position].title.toString()
            //val bundle = bundleOf("SavedTitle" to SavedTitle )
            val bundle = Bundle()
            bundle.putString("NoteTitle", mylist[position].title.toString())
            bundle.putString("Notebody", mylist[position].body.toString())
            bundle.putInt("id",mylist[position].id.toInt())
            bundle.putInt("UserId",mylist[position].userId.toInt())
            bundle.putString("SavedToken", Token)
            bundle.putString("Created", Created)
            bundle.putString("Updated", Updated)

            it.findNavController().navigate(R.id.action_show_to_addEdit, bundle)

        }



      holder.deletebtn.setOnClickListener {
          val retrofitBuilder =
              Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                  .baseUrl("https://api.notezz.com")
                  .build()
         val APIval = retrofitBuilder.create(APIService::class.java)
         APIval.DeleteNote("Bearer $Token", mylist[position].id).enqueue(object: Callback<StatusModel> {
              override fun onResponse(
                    call: Call<StatusModel>,
                    response: Response<StatusModel>
                ) {
                  if(response.isSuccessful){
                      Toast.makeText(it.context, response.code().toString() , Toast.LENGTH_LONG).show()
                  }else{
                      Toast.makeText(it.context, response.code().toString() , Toast.LENGTH_LONG).show()
                  }

                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<StatusModel>, t: Throwable) {
                    TODO("Not yet implemented")
                    Toast.makeText(it.context, t.toString(), Toast.LENGTH_LONG).show()
                }
            })


      }
    }

    override fun getItemCount(): Int {

        return mylist.size
    }



}
