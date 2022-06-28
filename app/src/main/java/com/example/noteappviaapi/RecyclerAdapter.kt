package com.example.noteappviaapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter( private val mylist: List<addNoteResponseModel>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView
        var itemDescription: TextView

        init {
            itemTitle = itemView.findViewById(R.id.titleTextview)
            itemDescription = itemView.findViewById(R.id.descriptionTextview)
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
            bundle.putInt("NoteId",mylist[position].id)
            it.findNavController().navigate(R.id.action_show_to_addEdit, bundle)
        }
    }

    override fun getItemCount(): Int {
        return mylist.size
    }



}
