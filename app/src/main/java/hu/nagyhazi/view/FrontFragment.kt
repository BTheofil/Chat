package hu.nagyhazi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import hu.nagyhazi.R
import hu.nagyhazi.adapter.UsersAdapter
import hu.nagyhazi.adapter.listener.AdapterListener
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.fragment_front.*

class FrontFragment: Fragment(), NavigationView.OnNavigationItemSelectedListener, AdapterListener {

    private lateinit var navController: NavController
    private lateinit var userAdapter: UsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val v = inflater.inflate(R.layout.fragment_front, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigationView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        users_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            userAdapter =
                UsersAdapter(this@FrontFragment)
            adapter = userAdapter
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(R.id.action_frontFragment_to_welcomeFragment)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initNavigationView(view: View) {
        val vac = view.findViewById<NavigationView>(R.id.nav_view)
        vac.setNavigationItemSelectedListener(this)
    }

    override fun onClickItem(user: User) {
        navController.navigate(R.id.action_frontFragment_to_chatFragment)
    }


}