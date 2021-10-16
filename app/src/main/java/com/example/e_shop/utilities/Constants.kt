package com.example.e_shop.utilities

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object Constants {
    const val USER :String = "users"
    const val ESHOP_PREFERENCE :String = "E-ShopPrefs"
    const val LOGGED_IN_USERNAME :String = "logged_in_username"
    const val EXTRA_USER_DETAILS :String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE  = 2
    const val PICK_IMAGE_REQUEST_CODE  = 1

    const val MALE:String = "Male"
    const val FEMALE:String = "Female"
    const val MOBILE:String = "mobile"
    const val GENDER:String = "gender"



    fun showImageChooser(activity: Activity){
        //an internet for launching the image selection of phone storage
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        //Launches the image selection of phone storage using the constants
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

}