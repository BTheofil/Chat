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

    private var userList: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        ViewHolder(holder.itemView)
            .bind(userList[position])
        holder.itemView.setOnClickListener{
            adapterListener.onClickItem(userList[position]);
        }
    }

    class ViewHolder(itemaView: View): RecyclerView.ViewHolder(itemaView){

        private val userName: TextView = itemaView.userName

        fun bind(user: User){

            user.apply {
                userName.text = name
            }
        }
    }
}