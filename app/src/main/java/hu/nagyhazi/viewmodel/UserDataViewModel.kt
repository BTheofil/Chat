package hu.nagyhazi.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import hu.nagyhazi.model.User


class UserDataViewModel {

    val usersDataLiveData: MutableLiveData<MutableList<User>> = MutableLiveData()
    private var originalData: MutableList<User> = ArrayList()

    private var database = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = database.getReference("users")

    fun getAllUser() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (data in dataSnapshot.children){
                    val temp = data.value
                    originalData.add(User(temp as MutableMap<String, String>))
                    Log.d("IN", originalData.size.toString())
                }

                usersDataLiveData.postValue(originalData)
                Log.d("IN", originalData[1].data["email"].toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }
}