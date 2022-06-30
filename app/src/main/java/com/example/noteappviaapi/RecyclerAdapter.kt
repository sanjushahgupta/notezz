package com.example.noteappviaapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviaapi.Model.addNoteResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecyclerAdapter(private val mylist: List<addNoteResponseModel>, val newToken: String, val Created: String, val Updated: String) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



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
            bundle.putInt("NoteId",mylist[position].id.toInt())
            bundle.putInt("UserId",mylist[position].userId.toInt())
            bundle.putString("SavedToken", newToken)
            bundle.putString("Created", Created)
            bundle.putString("Updated", Updated)

            it.findNavController().navigate(R.id.action_show_to_addEdit, bundle)
        }

      holder.deletebtn.setOnClickListener({
          val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
              .baseUrl("https://api.notezz.com")
              .build()


          val APIval = retrofitBuilder.create(APIService::class.java)
          val call = APIval.DeleteNote("Bearer $newToken" )


      })
    }

    override fun getItemCount(): Int {
        return mylist.size
    }



}
