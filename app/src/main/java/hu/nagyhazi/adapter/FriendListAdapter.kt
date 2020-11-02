package hu.nagyhazi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.nagyhazi.R
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.user_card.view.*

class FriendListAdapter(private var adapterListener: AdapterListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var user: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        )
    }

    override fun getItemCount() = user.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        FriendViewHolder(holder.itemView).bind(user[position])

        holder.itemView.setOnClickListener {
            adapterListener.onClickItem(user[position])
        }
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.userName

        fun bind(user: User){
            userName.text = user.name
        }
    }
}