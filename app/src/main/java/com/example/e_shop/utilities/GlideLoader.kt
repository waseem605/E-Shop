package com.example.e_shop.utilities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_shop.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(image: Any,imageView: ImageView){
        try {
            //Load the user image in the imageView

            Glide.with(context)
                .load(image) //URI of image
                .centerCrop()   //Scale type of the image
                .placeholder(R.drawable.ic_baseline_person_24) // A default placeHolder if failed
                .into(imageView)    //the view in which the imge will be loaded
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    //A function to load image from Uri or URL for the product image.
    fun loadProductPicture(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}