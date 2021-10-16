package com.example.e_shop.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.e_shop.R
import com.example.e_shop.firestore.FirestoreClass
import com.example.e_shop.model.User
import com.example.e_shop.utilities.Constants
import com.example.e_shop.utilities.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.util.jar.Manifest

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            //get the user details from intent as a ParcelableExtra
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        et_first_name.isEnabled = false
        et_first_name.setText(mUserDetails.firstName)

        et_last_name.isEnabled = false
        et_last_name.setText(mUserDetails.lastName)

        et_email.isEnabled = false
        et_email.setText(mUserDetails.email)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)

        btn_submit.setOnClickListener(this@UserProfileActivity)
    }



    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {
                    //Here we will chek if the permission is already allowed or request
                    //First of all we will check tha READ_EXTRA_STORAGE permission and if it is not
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@UserProfileActivity)

                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )

                    }
                }

                R.id.btn_submit -> {
                    if (validateUserProfileDetails()){
                        val userHashMap =HashMap<String,Any>()

                        val mobileNumber = et_number.text.toString().trim() {it <= ' '}
                        val gender = if (rb_male.isChecked){
                            Constants.MALE
                        }else{
                            Constants.FEMALE
                        }

                        if (mobileNumber.isNotEmpty()){
                            userHashMap[Constants.MOBILE]=mobileNumber.toLong()
                        }
                        userHashMap[Constants.GENDER]=gender

                        showProgressDialog(resources.getString(R.string.please_wait))

                        FirestoreClass().updateUserProfileData(this@UserProfileActivity,userHashMap)
                    }
                }

            }
        }
    }

    private fun validateUserProfileDetails():Boolean{
        return when {
            TextUtils.isEmpty(et_number.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //if permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //showErrorSnackBar("The storage permission is granted.", false)
                Constants.showImageChooser(this)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.Read_stoaragePermission_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    try {
                        //The uri of select image from phoneStorage
                        val selectImageUri = data.data!!
                        //iv_user_photo.setImageURI(Uri.parse(selectImageUri.toString()))
                        GlideLoader(this).loadUserPicture(selectImageUri,iv_user_photo)

                    }catch (e:Exception){
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            Log.e("Request Cancelled","Image selection cancelled")
        }
    }

}