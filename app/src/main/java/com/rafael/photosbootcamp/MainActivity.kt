package com.rafael.photosbootcamp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rafael.photosbootcamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private val PERMISSION_CODE_IMG_PICK = 1000
        private val IMAGE_PICK_CODE = 1001
        private val PERMISSION_CODE_CAMERA_CAPTURE = 2000
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPick.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE_IMG_PICK)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        binding.btnOpenCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE_CAMERA_CAPTURE)
                }else{
                    openCamera()
                }
            } else {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE_IMG_PICK -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show()
                }
            }

            PERMISSION_CODE_CAMERA_CAPTURE -> {
                if (grantResults.size > 1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }else{
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    @Suppress("DEPRECATION")
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivImage.setImageURI(data?.data)
        }
    }
}