package hu.nagyhazi.view

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import hu.nagyhazi.R
import hu.nagyhazi.adapter.AdapterListener
import hu.nagyhazi.adapter.FriendListAdapter
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.fragment_front.*

class FrontFragment: Fragment(), AdapterListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var friendListAdapter: FriendListAdapter
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val v = inflater.inflate(R.layout.fragment_front, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigationView(view)
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {FirebaseAuth.getInstance().signOut()
                            navController.navigate(R.id.action_frontFragment_to_welcomeFragment)
            }
            R.id.addFriend -> navController.navigate(R.id.action_frontFragment_to_addFriendFragment)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initNavigationView(view: View) {
        val vac = view.findViewById<NavigationView>(R.id.nav_view)
        vac.setNavigationItemSelectedListener(this)
    }

    private fun initRecyclerView() {
        friendList_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            friendListAdapter =
                FriendListAdapter(this@FrontFragment)
            adapter = friendListAdapter
        }
    }

    override fun onClickItem(user: User) {
        val bundle = bundleOf("user" to user)
    }
}