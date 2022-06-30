package com.example.noteappviaapi.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.Model.addNoteResponseModel
import com.example.noteappviaapi.Model.noteModel
import com.example.noteappviaapi.Model.updateModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentAddEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding:FragmentAddEditBinding
class AddEdit : Fragment() {
        private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(sharedPrefFile,
                   Context.MODE_PRIVATE)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
       // setHasOptionsMenu(true)
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.notezz.com")
            .build()

        binding.editTextTitle.setText(arguments!!.getString("NoteTitle"))
       binding.editTextDescription.setText( arguments!!.getString("Notebody"))


      // Toast.makeText(activity,"Authoriazation token is" + "$passedTokenFromRecyclerAndShow", Toast.LENGTH_LONG).show()

        binding.addbutton.setOnClickListener {

              val APIval = retrofitBuilder.create(APIService::class.java)
            var passedTokenFromLogin: String? = arguments!!.getString("SavedToken")
            val call = APIval.addNote("Bearer $passedTokenFromLogin",
                noteModel(binding.editTextTitle.text.toString() , binding.editTextDescription.text.toString()  ,"Active") )
               call.enqueue(object : Callback<addNoteResponseModel>{
                   override fun onResponse(
                       call: Call<addNoteResponseModel>,
                       response: Response<addNoteResponseModel>
                   ) {
                          if(response.isSuccessful){
                            //  Toast.makeText(activity,"note added successfully of userid" +response.body()!!.userId.toString(), Toast.LENGTH_LONG).show()

                              val SavedToken = passedTokenFromLogin.toString()
                              var Created= response.body()!!.created.toString()
                              var Updated = response.body()!!.updated.toString()
                               val bundles = bundleOf("SavedToken" to SavedToken, "Created" to Created )


                              it.findNavController().navigate(R.id.action_addEdit_to_show,bundles)

                          }else{
                            Toast.makeText(activity,"error is" + response.code().toString() , Toast.LENGTH_LONG).show()
                          }

                       }
                   override fun onFailure(call: Call<addNoteResponseModel>, t: Throwable) {
                          Toast.makeText(activity,"error"+ t.toString(), Toast.LENGTH_LONG).show()
                   }

               })
           }

      binding.Updatbutton.setOnClickListener {
          val userid = arguments!!.getInt("UserId")!!
          val noteid = arguments!!.getInt("NoteId")!!
          val notetitle = arguments!!.getString("NoteTitle")!!
          val noteBody = arguments!!.getString("Notebody")!!
          val noteCreated = arguments!!.getString("Created")!!
          val noteUpdated = arguments!!.getString("Updated")!!
          val passedTokenFromRecyclerAndShow = arguments!!.getString("SavedToken")!!


          val APIval = retrofitBuilder.create(APIService::class.java)
          val call = APIval.UpdateNote(
              "Bearer $passedTokenFromRecyclerAndShow",
              noteid,
              updateModel(
                  noteid,
                  binding.editTextTitle.text.toString(),
                  binding.editTextDescription.text.toString(),
                  "Active",
                  userid,
                  noteCreated,
                  noteUpdated
              )
          )

          call.enqueue(object : Callback<updateModel> {
              override fun onResponse(
                  call: Call<updateModel>,
                  response: Response<updateModel>
              ) {
                  if (response.isSuccessful) {

                      //it.findNavController().navigate(R.id.action_addEdit_to_show)
                      val bundles = bundleOf("SavedToken" to passedTokenFromRecyclerAndShow, "Created" to noteCreated, "Updated" to noteUpdated )
                      it.findNavController().navigate(R.id.action_addEdit_to_show,bundles)

                  }else{

                      Toast.makeText(
                          activity,
                          "error is" ,
                          Toast.LENGTH_LONG
                      ).show()
                  }

              }

              override fun onFailure(call: Call<updateModel>, t: Throwable) {
                  Toast.makeText(activity, "error T " + t.toString(), Toast.LENGTH_LONG).show()
              }

          })

      }
        return binding.root
    }
    //enable options menu in this fragment
   /* override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.savemenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(activity, "Save button clicked", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }*/

}
