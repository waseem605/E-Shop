package com.example.e_shop.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.e_shop.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()
        loginLink.setOnClickListener {
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        btn_register.setOnClickListener {
            validateRegisterDetails()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(register_toolbar)

        val  actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
        }
        register_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    //validation
    private fun validateRegisterDetails():Boolean{
        return when{
            TextUtils.isEmpty(firstNameR.text.toString().trim(){it<= ' '})->{
                showErrorSnackBar(resources.getString(R.string.error_msg_f_name),true)
                false
            }
            TextUtils.isEmpty(lastNameR.text.toString().trim(){it<= ' '})->{
                System.out.println("==========last name====")

                showErrorSnackBar(resources.getString(R.string.error_msg_last_name),true)
                false
            }
            TextUtils.isEmpty(emailID.text.toString().trim(){it<= ' '})->{
                showErrorSnackBar(resources.getString(R.string.error_msg_email_),true)
                false
            }
            TextUtils.isEmpty(passwordR.text.toString().trim(){it<= ' '})->{
                showErrorSnackBar(resources.getString(R.string.error_msg_password_),true)
                false
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim(){it<= ' '})->{
                showErrorSnackBar(resources.getString(R.string.error_msg_conf_passw_),true)
                false
            }
            passwordR.text.toString().trim(){it<= ' '} != confirmPassword.text.toString().trim(){it<= ' '}->{
                showErrorSnackBar(resources.getString(R.string.error_msg_conf_and_passw_),true)
                false
            }
            !boxTerm_condition.isChecked ->{
                showErrorSnackBar(resources.getString(R.string.error_msg_terms_),true)
                false
            }
            else ->{
                showErrorSnackBar("Your details are valid",false )
                true
            }
        }
    }

    private fun registerUser(){
        //chack with validate function if the entries are valid or not
        if(validateRegisterDetails()){
            val email  = emailID.text.toString().trim(){it <=' '}
            val password  = passwordR.text.toString().trim(){it <=' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener (
                    OnCompleteListener<AuthResult> { task ->

                        if (task.isSuccessful){
                            //Firebase create user
                            val firebaseUser : FirebaseUser = task.result!!.user!!

                            showErrorSnackBar("You are Register Successfully",false)
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    })
                }
        }

}