package com.example.e_shop.utilities

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_shop.R

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageUri: Uri,imageView: ImageView){
        try {
            //Load the user image in the imageView
            Glide.with(context)
                .load(imageUri) //URI of image
                .centerCrop()   //Scale type of the image
                .placeholder(R.drawable.ic_baseline_person_24) // A default placeHolder if failed
                .into(imageView)    //the view in which the imge will be loaded
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}