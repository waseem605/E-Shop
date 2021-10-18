package com.example.e_shop.activitiesUI.activities

import android.os.Bundle
import android.widget.Toast
import com.example.e_shop.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setActionBar()


    }

    private fun setActionBar(){

        setSupportActionBar(forgot_toolbar)
        val activityBar = supportActionBar
        if (activityBar != null){
            activityBar.setDisplayHomeAsUpEnabled(true)
            activityBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
        }

        forgot_toolbar.setNavigationOnClickListener { onBackPressed() }

        submit_forgot_btn.setOnClickListener {
            val email = email_forgot.text.toString().trim() { it <= ' '}
            if (email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.error_msg_email_), true)
            }else{
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if (task.isSuccessful){
                            Toast.makeText(this,"Email send successfully",Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.toString(), true)
                        }

                    }
            }
        }
    }
}