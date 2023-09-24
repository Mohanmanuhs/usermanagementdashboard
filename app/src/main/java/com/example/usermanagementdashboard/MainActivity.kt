package com.example.usermanagementdashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.usermanagementdashboard.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var im="mibah"
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.upload.setOnClickListener {
            var intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }

        var dbc = db.collection("Solutions")

        binding.info.setOnClickListener {
            var user = User(im, binding.largetext.text.toString(), binding.smalltext.text.toString())
            dbc.document("infooverload").collection("info").add(user)

        }
        binding.harrs.setOnClickListener {
            var user = User(im, binding.largetext.text.toString(), binding.smalltext.text.toString())
            dbc.document("harassment").collection("harrs").add(user)

        }
        binding.mental.setOnClickListener {
            var user = User(im, binding.largetext.text.toString(), binding.smalltext.text.toString())
            dbc.document("mental health").collection("ment").add(user)

        }
        binding.social.setOnClickListener {
            var user = User(im, binding.largetext.text.toString(), binding.smalltext.text.toString())
            dbc.document("socialmediapressure").collection("soc").add(user)

        }
    }
    private var imageLauncher =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (it.data != null) {
                val ref = Firebase.storage.reference.child(
                    "Photo/" + System.currentTimeMillis() + "." + getFileType(it.data!!.data!!)
                )
                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        Firebase.database.reference.child("Photo").push()
                            .setValue(it.toString())
                        im = it.toString()
                    }
                }
            }
        }
    }

    private fun getFileType(data: Uri?): String? {
        val r = contentResolver
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getMimeTypeFromExtension(r.getType(data!!))

    }

}