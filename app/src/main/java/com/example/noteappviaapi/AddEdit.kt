package com.example.noteappviaapi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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
        binding.editTextDescription.setText(arguments!!.getString("Notebody"))
        val noteid = arguments!!.getInt("NoteId")

       // Toast.makeText(activity,"note added successfully of id" + noteid, Toast.LENGTH_LONG).show()


      binding.addbutton.setOnClickListener {
          val passedTokenFromLogin = arguments?.getString("SavedToken")!!
              val APIval = retrofitBuilder.create(APIService::class.java)
            val call = APIval.addNote("Bearer $passedTokenFromLogin",noteModel(/*binding.editTextTitle.text.toString()*/ "Life" , /*binding.editTextDescription.text.toString()*/ "is beautiful" ,"Active") )

               call.enqueue(object : Callback<addNoteResponseModel>{
                   override fun onResponse(
                       call: Call<addNoteResponseModel>,
                       response: Response<addNoteResponseModel>
                   ) {
                          if(response.isSuccessful){
                              Toast.makeText(activity,"note added successfully of userid" +response.body()!!.userId.toString(), Toast.LENGTH_LONG).show()

                              val UserToken = passedTokenFromLogin
                               val bundles = bundleOf("UserToken" to UserToken )

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
