package com.example.e_shop.activitiesUI.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.e_shop.R
import com.example.e_shop.firestore.FirestoreClass
import com.example.e_shop.model.User
import com.example.e_shop.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        forget_password.setOnClickListener(this)
        loginBtn.setOnClickListener(this)
        signupLink.setOnClickListener(this)

    }

    override fun onClick(view: View?){
        if (view!=null){
            when(view.id){
                R.id.forget_password ->{
                    val intent = Intent(this, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.loginBtn ->{
                   loginUser()
                }
                R.id.signupLink ->{
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun loginUser(){
        if (validateLoginDetails()){
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = emailLg.text.toString().trim() { it <= ' '}
            val password = passwordLg.text.toString().trim() { it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {  task ->

                    if (task.isSuccessful){

                        FirestoreClass().getUserDetails(this@LoginActivity)
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }

        }
    }

    private fun validateLoginDetails() :Boolean{
        return when {
            TextUtils.isEmpty(emailLg.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email_), true)
                false
            }
            TextUtils.isEmpty(passwordLg.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_password_), true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun userLoggedInSuccess(user:User){
        hideProgressDialog()

        if (user.profileCompleted==0){
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}