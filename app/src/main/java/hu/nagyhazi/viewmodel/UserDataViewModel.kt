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
        originalData.clear()
        getAllUser()
    }

    private fun getAllUser() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAllUsersData(dataSnapshot.value as Map<String?, Any?>?)
                usersDataLiveData.postValue(originalData)
                //var a = mutableMapOf<String, Any>()
                //a = dataSnapshot.value

                /*for (data in dataSnapshot.children){
                    val temp = data.value
                    val a: MutableMap<String, User>
                    a.put(temp[0], temp.)
                    //originalData.add(temp as MutableList<User>)
                    //originalData.add(User(temp as MutableMap<String, User>))
                    Log.d("IM_IN_HERE", originalData[3].name)
                }*/

                //Log.d("IN", originalData[1].data["email"].toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

    private fun getAllUsersData(users: Map<String?, Any?>?) {
        //val phoneNumbers: ArrayList<Long?> = ArrayList()

        //iterate through each user, ignoring their UID
        if (users != null) {
            for ((_, value) in users) {

                //Get user map
                val singleUser = value as Map<*, *>
                singleUser.values
                val name = singleUser["name"] as String
                val email = singleUser["email"] as String
                val conversation = singleUser["conversation"] as ArrayList<String>
                originalData.add(User(name,email,conversation))
                /*for ((_, value) in singleUser){
                    val data = value as Map<*,*>
                    val a = data["name"] as String
                    val b = data["email"] as String
                    val c = data["conversation"] as ArrayList<String>
                    originalData.add(User(a,b,c))
                }*/

                /*val a = singleUser["name"] as String
                val b = singleUser["email"] as String
                val c = singleUser["conversation"] as ArrayList<String>
                originalData.add(User(a,b,c))*/
                //Get phone field and append to list
                //phoneNumbers.add(singleUser["phone"] as Long?)
            }
        }
    }

    fun loadMsg(clickedUser: User){
        val actualUser = getCurrentUserData()
        msgDatabase.child(actualUser.name + clickedUser.name)

        loadMessage.clear()
        msgDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAllMessageData(dataSnapshot.value as Map<String?, Any?>?)
                usersMessageDataLiveData.postValue(loadMessage)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun getAllMessageData(message: Map<String?, Any?>?){
        val currentUser = getCurrentUserData()
        if (message != null) {
            for ((key, value) in message) {
                for (kapcsolatok in currentUser.conversation){
                    if (kapcsolatok == key){
                        val info = value as ArrayList<*>
                        for (part in info){
                            part as HashMap<*, *>
                            val id = part["senderId"] as String
                            val conti = part["content"] as String
                            loadMessage.add(Message(id,conti))
                        }
                        //loadMessage.add(info)
                        break
                    }
                }

                //Get user map
                //val singleUser = value as ArrayList<*>
                //val name = singleUser["content"] as String
                //val email = singleUser["senderId"] as String
                //loadMessage.add(Message(name,email))

            }
        }
    }

    fun startMessages(clickedUser: User){
        val actualUser = getCurrentUserData()
        msgDatabase.child(actualUser.name + clickedUser.name)
        loadMessage.add(Message(actualUser.name, "Now we can talk"))
        msgDatabase.child(actualUser.name + clickedUser.name).setValue(loadMessage)

        //setup two users conversation lists
        actualUser.conversation.add(actualUser.name + clickedUser.name)
        clickedUser.conversation.add(actualUser.name + clickedUser.name)

        mDatabase.child(actualUser.name).child("conversation").setValue(actualUser.conversation)
        mDatabase.child(clickedUser.name).child("conversation").setValue(clickedUser.conversation)

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