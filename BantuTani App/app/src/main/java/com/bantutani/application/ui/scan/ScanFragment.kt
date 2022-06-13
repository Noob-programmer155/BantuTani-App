package com.bantutani.application.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bantutani.application.R
import com.bantutani.application.data.entitiy.predict.DataPredict
import com.bantutani.application.databinding.FragmentScanBinding
import com.bantutani.application.ui.camera.CameraActivity
import com.bantutani.application.ui.result.ResultActivity
import com.bantutani.application.utils.reduceImage
import com.bantutani.application.utils.rotateBitmap
import com.bantutani.application.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ScanFragment : Fragment() {
    private lateinit var scan: FragmentScanBinding
    private var getFile: File? = null
    private val viewModel : ScanViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scan = FragmentScanBinding.bind(view)

        if (!allPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        scan.camera.setOnClickListener {
            startCameraX()
        }
        scan.gallery.setOnClickListener {
            startGallery()
        }
        scan.button.setOnClickListener {
            if(getFile ==null){
                Toast.makeText(context, getString(R.string.empty_img_scan), Toast.LENGTH_SHORT).show()

            }else {
                val file = reduceImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    requestImageFile
                )
                viewModel.sendData(imageMultipart)
                viewModel.fetchdata().observe(viewLifecycleOwner,{
                    if (it != null){
                        val data = DataPredict(
                            it.image,
                            it.otherNames,
                            it.attributePlantsType,
                            it.dateUpdate,
                            it.name,
                            it.description,
                            it.id,
                            it.authorPlantsAttribute
                        )
                        val moveWithObjectIntent = Intent(requireContext(), ResultActivity::class.java)
                        moveWithObjectIntent.putExtra(ResultActivity.EXTRA_DATA, data)
                        requireContext().startActivity(moveWithObjectIntent)
                    }
                })
            }
        }
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(this, getString(R.string.permission), Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun startCameraX() {
        val intent = Intent(activity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            scan.progressBar.visibility = View.VISIBLE
        } else {
            scan.progressBar.visibility = View.INVISIBLE
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            scan.previewImageView.setImageURI(selectedImg)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            scan.previewImageView.setImageBitmap(result)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_MEDIA_LOCATION)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)


    }
}