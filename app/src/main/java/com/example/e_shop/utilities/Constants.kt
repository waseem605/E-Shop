package com.example.e_shop.utilities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USER :String = "users"
    const val PRODUCTS: String = "products"

    const val ESHOP_PREFERENCE :String = "E-ShopPrefs"
    const val LOGGED_IN_USERNAME :String = "logged_in_username"
    const val EXTRA_USER_DETAILS :String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE  = 2
    const val PICK_IMAGE_REQUEST_CODE  = 1

    const val MALE:String = "Male"
    const val FEMALE:String = "Female"
    const val MOBILE:String = "mobile"
    const val GENDER:String = "gender"
    const val IMAGE:String = "image"
    const val FIRST_NAME:String = "firstName"
    const val LAST_NAME:String = "lastName"
    const val COMPLETE_PROFILE:String = "profileCompleted"
    const val USER_PROFILE_IMAGE:String = "user_profile_image"

    const val PRODUCT_IMAGE:String = "product_image"
    const val USER_ID:String = "user_id"



    fun showImageChooser(activity: Activity){
        //an internet for launching the image selection of phone storage
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        //Launches the image selection of phone storage using the constants
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity,uri:Uri?): String? {

        //MimTypeMap: two-way map that maps MiME-types to file extension and vice versa
        //getSingleton(): get the singleton instance of MimeTypeMap
        //getExtensionFromMimType: return the registered extension for the given Mime type
        //contentResolver.getType : return the type of the given content URL
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}