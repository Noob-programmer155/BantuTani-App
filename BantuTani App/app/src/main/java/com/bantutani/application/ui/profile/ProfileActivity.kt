package com.bantutani.application.ui.profile


import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bantutani.application.R
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.databinding.ActivityProfileBinding
import com.bantutani.application.ui.camera.CameraActivity
import com.bantutani.application.ui.login.LoginActivity
import com.bantutani.application.ui.main.MainActivity
import com.bantutani.application.utils.rotateBitmap
import com.bantutani.application.utils.uriToFile
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class ProfileActivity : AppCompatActivity() {
    private lateinit var profile: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels(){ProfileFactory(this)}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.no_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profile.root)
        viewModel.getProfileDetail()
        viewModel.fetchData().observe(this,{
            if (it != null){
                profile.apply {
                    val glideUrl = GlideUrl(
                        "http://104.196.207.218/api/user/v1/data/image/${it.image}",
                        LazyHeaders.Builder()
                            .addHeader("Accept", "*/*")
                            .addHeader("Authorization", it.token)
                            .build()
                    )
                    Glide.with(this@ProfileActivity)
                        .load(glideUrl)
                        .into(imgProfile)
                    emailInfo.text = it.email
                    namaLkp.text = it.fullName
                    usernameInfo.text = it.username
                    passwordInfo.text = "**********"
                }
            }
        })
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )

            profile.backBtn.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            profile.edit.setOnClickListener {
                showDialog()
            }

        }
        profile.logout.setOnClickListener{
            setLogout()
        }
    }


    private fun setLogout() {
        val logout = SessionPref(this)
        logout.deleteSession()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.edit_profile)

        val closeBtn = dialog.findViewById<ImageView>(R.id.close)
        val changeImg = dialog.findViewById<FloatingActionButton>(R.id.edit_img)
        val profil = dialog.findViewById<TextView>(R.id.img_profile)
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
        changeImg.setOnClickListener {
            methodDialog()
        }


        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun methodDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_picker)
        val gallery = dialog.findViewById<ImageButton>(R.id.pick_gallery)
        val camera = dialog.findViewById<ImageButton>(R.id.pick_camera)
        val cancel = dialog.findViewById<TextView>(R.id.cancel)
        dialog.show()

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        gallery.setOnClickListener {
            startGallery()
        }
        camera.setOnClickListener {
            startCameraX()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }



    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

//            profile.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ProfileActivity)
//            binding.previewImageView.setImageURI(selectedImg)
        }
    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}


