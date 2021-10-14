package com.example.e_shop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.e_shop.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

    }
    fun showErrorSnackBar(message:String,errorMessage:Boolean){
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        System.out.println("==========SnackBar====")
        if (errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.snack_bar_error))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.snack_bar_success))
        }
        snackBar.show()
    }
}