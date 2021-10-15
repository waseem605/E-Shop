package com.example.e_shop.firestore
import android.app.Activity
import android.util.Log
import com.example.e_shop.activities.RegisterActivity
import com.example.e_shop.model.User
import com.example.e_shop.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()
    private lateinit var mDbReference: DatabaseReference


    fun registerUSer(activity: RegisterActivity,userInfo:User){
        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.USER)

        mDbReference.child(userInfo.id).setValue(userInfo)
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }.addOnFailureListener{ e ->
                activity.hideProgressDialog()
                Log.e(this.toString(),"Error while registering the user",e)
            }

    }

    fun getCurrentUserId():String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUSerID = ""
        if (currentUser != null){
            currentUSerID = currentUser.uid
        }
        return currentUSerID
    }

    fun getUserDetails(activity: Activity){
        mDbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    Log.i(activity.toString(),user.firstName)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

}