package hu.nagyhazi.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import hu.nagyhazi.model.Message
import hu.nagyhazi.model.User

class UserDataViewModel(application: Application): AndroidViewModel(application) {

    val usersDataLiveData: MutableLiveData<MutableList<User>> = MutableLiveData()
    private var originalData: MutableList<User> = ArrayList()

    private var database = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = database.getReference("users")

    private var msgDatabase: DatabaseReference = database.getReference("message")
    val usersMessageDataLiveData: MutableLiveData<MutableList<Message>> = MutableLiveData()
    private var loadMessage: MutableList<Message> = ArrayList()

    init {
        getAllUser()
    }

    private fun getAllUser() {
        originalData.clear()
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAllUsersData(dataSnapshot.value as Map<String?, Any?>?)
                usersDataLiveData.postValue(originalData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

    private fun getAllUsersData(users: Map<String?, Any?>?) {
        if (users != null) {
            for ((_, value) in users) {
                val singleUser = value as Map<*, *>
                val name = singleUser["name"] as String
                val email = singleUser["email"] as String
                val conversation = singleUser["conversation"] as ArrayList<String>
                originalData.add(User(name,email,conversation))
            }
        }
    }

    fun sendMsg(text: String, clickedUser: User){
        val actualUserName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
        val messageStorageName = actualUserName + clickedUser.name
        actualUserName?.let { Message(it, text) }?.let { loadMessage.add(it) }
        msgDatabase.child(messageStorageName).setValue(loadMessage)
    }

    fun loadMsg(clickedUser: User){
        loadMessage.clear()

        val actualUserName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
        val messageStorageName = actualUserName + clickedUser.name
        msgDatabase.child(messageStorageName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAllMessageData(dataSnapshot.value)
                usersMessageDataLiveData.postValue(loadMessage)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun getAllMessageData(message: Any?){
        if (message != null) {
            message as ArrayList<*>
            for (part in message){
                part as HashMap<*,*>
                val id = part["senderId"] as String
                val cntnt = part["content"] as String
                loadMessage.add(Message(id,cntnt))
            }
        }
    }

    fun startMessages(clickedUser: User){
        val actualUser = getCurrentUserData()

        //setup two users conversation lists
        if (!(actualUser.conversation.contains(actualUser.name + clickedUser.name))){
            actualUser.conversation.add(actualUser.name + clickedUser.name)
            clickedUser.conversation.add(actualUser.name + clickedUser.name)

            mDatabase.child(actualUser.name).child("conversation").setValue(actualUser.conversation)
            mDatabase.child(clickedUser.name).child("conversation").setValue(clickedUser.conversation)

            loadMessage.add(Message(actualUser.name, "Now we can talk"))
            msgDatabase.child(actualUser.name + clickedUser.name).setValue(loadMessage)
        }

    }

    private fun getCurrentUserData(): User {
        val auth = FirebaseAuth.getInstance()
        val what = auth.currentUser?.email
        for (user in originalData){
            if (user.email == what){
                return user
            }
        }
        val a = arrayListOf("Empty")
        return User("Empty","Empty", a)
    }
}