package hu.nagyhazi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import hu.nagyhazi.model.User


class UserDataViewModel {

    val movieDataLiveData: MutableLiveData<MutableList<User>> = MutableLiveData()
    private var originalData: MutableList<User> = ArrayList()

    private var database = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = database.getReference("users")

    fun getAllUser() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val originalData = dataSnapshot.value
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        movieDataLiveData.postValue(originalData)
        mDatabase.addValueEventListener(postListener)
    }
}