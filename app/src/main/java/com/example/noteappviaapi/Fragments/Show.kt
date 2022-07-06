package com.example.noteappviaapi.Fragments
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviaapi.APIService
import com.example.noteappviaapi.MainActivity
import com.example.noteappviaapi.Model.addNoteResponseModel
import com.example.noteappviaapi.R
import com.example.noteappviaapi.RecyclerAdapter
import com.example.noteappviaapi.databinding.FragmentShowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var binding:FragmentShowBinding

class Show : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                System.exit(0)
            }
        })
        binding = DataBindingUtil.inflate(inflater, com.example.noteappviaapi.R.layout.fragment_show, container, false)
        setHasOptionsMenu(true)
        val created =arguments!!.getString("Created")
        val Updated = arguments!!.getString("Updated")
        val APIval = MainActivity().APIClient().create(APIService::class.java)
        val passedTokenFromLogin = arguments!!.getString("SavedToken")!!
        val id = arguments!!.getInt("id")
        val call = APIval.ShowNote("Bearer $passedTokenFromLogin" )

       call.enqueue(object:Callback<List<addNoteResponseModel>>{
            override fun onResponse(call: Call<List<addNoteResponseModel>>, response: Response<List<addNoteResponseModel>>) {

                if (response.isSuccessful){
                    binding.recyclerView.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = response.body()?.let { RecyclerAdapter(it,"$passedTokenFromLogin","$created","$Updated") }
                    }
                }else{
                    Toast.makeText(
                        activity,
                        response.code().toString(),
                        Toast.LENGTH_LONG
                    ).show();

                }
            }

            override fun onFailure(call: Call<List<addNoteResponseModel>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        return binding.root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)

    }
    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addnotemenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val passedTokenFromLogin = arguments!!.getString("SavedToken")!!
        val navController: NavController = view?.let { Navigation.findNavController(this.view!!) }!!
        val bundle = Bundle()
        bundle.putString("NoteTitle","")
        bundle.putString("Notebody", "")
        bundle.putInt("id", 0)
        bundle.putInt("UserId", 0)
        bundle.putString("SavedToken", "$passedTokenFromLogin")
        bundle.putString("Created", "")
        bundle.putString("Updated", "")
        navController.navigate(R.id.action_show_to_addEdit,bundle)
        return super.onOptionsItemSelected(item)
    }




}
