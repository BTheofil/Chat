package hu.nagyhazi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.nagyhazi.R
import hu.nagyhazi.adapter.listener.AdapterListener
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.user_card.view.*

class UsersAdapter(private var adapterListener: AdapterListener): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var listOfUsers: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewHolder(holder.itemView)
            .bind(listOfUsers[position])
        holder.itemView.setOnClickListener{
            adapterListener.onClickItem(listOfUsers[position])
        }
    }

    fun submitList(usersList: List<User>) {
        listOfUsers = listOf()
        listOfUsers = usersList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val userName: TextView = itemView.userName
        private val userEmail: TextView = itemView.userEmail

        fun bind(user: User){

            user.apply {
                userName.text = name
                userEmail.text = email
            }
        }
    }
}