package hu.nagyhazi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.nagyhazi.R
import hu.nagyhazi.adapter.AdapterListener
import hu.nagyhazi.adapter.FriendListAdapter
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.fragment_front.*

class FrontFragment: Fragment(), AdapterListener {

    private lateinit var friendListAdapter: FriendListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_front, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
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