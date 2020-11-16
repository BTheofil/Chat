package hu.nagyhazi.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hu.nagyhazi.model.User
import java.lang.NullPointerException

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
            ?: throw NullPointerException("UID is null")}")

    fun initCurrentUserIfFirstTime(onComplete:() -> Unit){
        currentUserDocRef.get().addOnSuccessListener {
            if(!it.exists()){
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", null)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }
    }

    fun updataCurrentUser(name: String = "", profilePicture: String? = null){
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()){
            userFieldMap["name"] = name
        }
        if (profilePicture != null){
            userFieldMap["profilePicture"] = profilePicture
        }
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit){
        currentUserDocRef.get().addOnSuccessListener {
            onComplete(it.toObject(User::class.java)!!)
        }
    }
}