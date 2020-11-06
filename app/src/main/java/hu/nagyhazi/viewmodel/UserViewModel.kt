package hu.nagyhazi.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel{

    fun getAllUser(){
        val fUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Useres")

        val ref = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        postReference.addValueEventListener(postListener)
    }
}