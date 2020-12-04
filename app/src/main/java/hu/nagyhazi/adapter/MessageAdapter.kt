package hu.nagyhazi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.nagyhazi.R
import hu.nagyhazi.model.Message
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.message_bubble.view.*

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var messages: List<Message> = arrayListOf(Message("asd", "asd"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_bubble, parent, false)
        return MessageAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        MessageAdapter.ViewHolder(holder.itemView)
            .bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun submitList(usersList: List<Message>) {
        messages = usersList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val userName: TextView = itemView.senderName
        private val msgWritten: TextView = itemView.msg

        fun bind(message: Message){

            message.apply {
                userName.text = senderId
                msgWritten.text = content
            }
        }
    }
}