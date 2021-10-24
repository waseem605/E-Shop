package com.example.e_shop.firestore
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.e_shop.activitiesUI.activities.*
import com.example.e_shop.activitiesUI.uiFragment.ProductFragment
import com.example.e_shop.model.Product
import com.example.e_shop.model.User
import com.example.e_shop.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    private lateinit var mDbReference: DatabaseReference


    fun registerUSer(activity: RegisterActivity, userInfo:User){

        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.USER)

        mDbReference.child(getCurrentUserId()).setValue(userInfo)
            .addOnSuccessListener {
                //activity.userRegistrationSuccess()
            }.addOnFailureListener{ e ->
                //activity.hideProgressDialog()
                Log.e(this.toString(),"Error while registering the user",e)
            }


/*
        mFireStore.collection(Constants.USER)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
*/
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
        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.USER).child(getCurrentUserId())

        mDbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    Log.i(activity.toString(),user.firstName)
                    val sharedPreferences = activity.getSharedPreferences(Constants.ESHOP_PREFERENCE,Context.MODE_PRIVATE)

                    val editor:SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(
                        Constants.LOGGED_IN_USERNAME,"${user.firstName} ${user.lastName}"
                    )
                    editor.apply()
                    when (activity){
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                        is SettingsActivity -> {
                            activity.userDetailsSuccess(user)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

    fun updateUserProfileData(activity: Activity,userHashMap:HashMap<String,Any>){
        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.USER).child(getCurrentUserId())

        mDbReference.updateChildren(userHashMap)
            .addOnSuccessListener {  }
            .addOnFailureListener {
                when(activity){
                    is UserProfileActivity ->{

                        activity.userProfileUpdateSuccess()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                "Error while updating",
                )
            }
    }

    fun uploadImageToCloudStorage(activity: Activity,imageFileURI:Uri?,imageType:String){
        val sRef :StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis()+"."
        + Constants.getFileExtension(activity,imageFileURI)
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapshot ->
            //the image upload is success
            Log.e("Firebase image uri", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())

            //get the downloadable uri from the task snapshot
            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                Log.e("Downloadable image uri", uri.toString())
                when(activity){
                    is UserProfileActivity ->{
                        activity.imageUploadSuccess(uri.toString())
                    }
                    is AddProductActivity -> {
                        activity.imageUploadSuccess(uri.toString())
                    }
                }

            }
        }
            .addOnFailureListener{exception->
                when(activity){
                    is UserProfileActivity ->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, exception.message,exception)

            }

    }

    fun uploadProductDetails(activity:AddProductActivity,productInfo:Product){
        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.PRODUCTS).push()

        mDbReference.setValue(productInfo)
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }.addOnFailureListener {e->
                activity.hideProgressDialog()
                Log.e(this.toString(),"Error while uploading product details",e)
            }

        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                // Here call a function of base activity for transferring the result to it.
                activity.productUploadSuccess()
            }.addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )

            }
    }


    fun getProductsList(fragment: Fragment) {

        // The collection name for PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is ProductFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ProductFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }


        ////////////////////////////////
        /*
        mDbReference = FirebaseDatabase.getInstance().reference.child(Constants.PRODUCTS)

        mDbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val productsList: ArrayList<Product> = ArrayList()
                var productModel = snapshot.getValue(Product::class.java)
                if (productModel != null){
                    // Here we have created a new instance for Products ArrayList.
                    if (productModel.user_id.equals(getCurrentUserId())){
                        productModel.product_id = productModel.product_id
                        productsList.add(productModel)
                    }
                }
                when(fragment){
                    is ProductFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
*/

    }

}