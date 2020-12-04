package hu.nagyhazi.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import hu.nagyhazi.model.User


class UserDataViewModel(application: Application): AndroidViewModel(application) {

    val usersDataLiveData: MutableLiveData<MutableList<User>> = MutableLiveData()
    private var originalData: MutableList<User> = ArrayList()

    private var database = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = database.getReference("users")

    init {
        getAllUser()
    }

    private fun getAllUser() {
        originalData.clear()
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAllUsersData(dataSnapshot.value as Map<String?, Any?>?)
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

                usersDataLiveData.postValue(originalData)
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
        println(originalData.toString())
    }
}