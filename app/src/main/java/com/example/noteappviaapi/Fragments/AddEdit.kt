package com.example.noteappviaapi.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.addNoteResponseModel
import com.example.noteappviaapi.Model.noteModel
import com.example.noteappviaapi.Model.updateModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.databinding.FragmentAddEditBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private lateinit var binding:FragmentAddEditBinding
class AddEdit : Fragment() {
        private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        // setHasOptionsMenu(true)



        binding.editTextTitle.setText(arguments!!.getString("NoteTitle"))
        binding.editTextDescription.setText(arguments!!.getString("Notebody"))

        val id = arguments!!.getInt("id")!!

        binding.Allnotes.setOnClickListener({
            it.findNavController().navigate(R.id.action_addEdit_to_show)
        })

        binding.addbutton.setOnClickListener {
                if (id == 0) {
                    val APIval = MainActivity().APIClient().create(APIService::class.java)
                    var passedTokenFromLogin: String? = arguments!!.getString("SavedToken")
                    val call = APIval.addNote(
                        "Bearer $passedTokenFromLogin",
                        noteModel(
                            binding.editTextTitle.text.toString(),
                            binding.editTextDescription.text.toString(),
                            "Active"
                        )
                    )
                    call.enqueue(object : Callback<addNoteResponseModel> {
                        override fun onResponse(
                            call: Call<addNoteResponseModel>,
                            response: Response<addNoteResponseModel>
                        ) {
                            if (response.isSuccessful) {
                                //  Toast.makeText(activity,"note added successfully of userid" +response.body()!!.userId.toString(), Toast.LENGTH_LONG).show()

                                val SavedToken = passedTokenFromLogin.toString()
                                var Created = response.body()!!.created.toString()
                                var Updated = response.body()!!.updated.toString()
                                var id = response.body()!!.id.toInt()
                                val bundles =
                                    bundleOf(
                                        "SavedToken" to SavedToken,
                                        "Created" to Created,
                                        "id" to id
                                    )

                                it.findNavController()
                                    .navigate(R.id.action_addEdit_to_show, bundles)

                            } else {
                                Toast.makeText(
                                    activity,
                                    "error is" + response.code().toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }

                        override fun onFailure(call: Call<addNoteResponseModel>, t: Throwable) {
                            Toast.makeText(activity, "error" + t.toString(), Toast.LENGTH_LONG)
                                .show()
                        }

                    })
                } else {
                    val userid = arguments!!.getInt("UserId")!!
                    val notetitle = arguments!!.getString("NoteTitle")!!
                    val noteBody = arguments!!.getString("Notebody")!!
                    val noteCreated = arguments!!.getString("Created")!!
                    val noteUpdated = arguments!!.getString("Updated")!!
                    val passedTokenFromRecyclerAndShow = arguments!!.getString("SavedToken")!!


                    val APIval = MainActivity().APIClient().create(APIService::class.java)
                    val call = APIval.UpdateNote(
                        "Bearer $passedTokenFromRecyclerAndShow",
                        id,
                        updateModel(
                            id,
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
                                val bundles = bundleOf(
                                    "SavedToken" to passedTokenFromRecyclerAndShow,
                                    "Created" to noteCreated,
                                    "Updated" to noteUpdated,
                                    "id" to id
                                )
                                it.findNavController()
                                    .navigate(R.id.action_addEdit_to_show, bundles)

                            } else {

                                Toast.makeText(
                                    activity,
                                    "value of id is " + id,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }

                        override fun onFailure(call: Call<updateModel>, t: Throwable) {
                            Toast.makeText(activity, "error T " + t.toString(), Toast.LENGTH_LONG)
                                .show()
                        }

                    })

                }
            }
        return binding.root
    }
    //enable options menu in this fragment
  /* override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.allnotesmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      // val navController: NavController = view?.let { Navigation.findNavController(this.view!!) }!!
      //  navController.navigate(R.id.show)
        Toast.makeText(activity, "All notes button clicked", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }*/

}
