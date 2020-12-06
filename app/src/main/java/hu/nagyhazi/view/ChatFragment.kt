package hu.nagyhazi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import hu.nagyhazi.R
import hu.nagyhazi.adapter.MessageAdapter
import hu.nagyhazi.model.User
import hu.nagyhazi.viewmodel.UserDataViewModel
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    private lateinit var messageAdapter: MessageAdapter
    lateinit var userDataViewModel: UserDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_chat, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)

        val selectedUser = arguments?.get ("toUser") as User

        initRecycler()
        subscribeLiveData(selectedUser)
        initButton(selectedUser)
    }

    private fun initButton(selectedUser: User) {
        button_chatbox_send.setOnClickListener {
            val text = edittext_chatbox.text.toString()
            edittext_chatbox.text.clear()
            userDataViewModel.sendMsg(text, selectedUser)
        }
    }

    private fun subscribeLiveData(selectedUser: User) {
        userDataViewModel.loadMsg(selectedUser)
        userDataViewModel.usersMessageDataLiveData.observe(viewLifecycleOwner, Observer{
            messageAdapter.submitList(it)
        })
    }

    private fun initRecycler() {
        recyclerview_message_list.apply {
            layoutManager = LinearLayoutManager(context)
            messageAdapter = MessageAdapter()
            adapter = messageAdapter
        }
    }
}
