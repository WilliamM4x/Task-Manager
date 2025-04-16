package com.devmax.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.devmax.Untils.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import de.hdodenhof.circleimageview.CircleImageView
import android.Manifest
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import androidx.activity.result.ActivityResult

import androidx.core.app.ActivityCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream

class Profile : AppCompatActivity() {

    private val PERMISSION_REQUEST_CAMERA=0
    private val PERMISSION_REQUEST_MEDIA=1
    var imageBit : Bitmap? = null

    val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser?.uid
    val dataBaseRef = FirebaseDatabase.getInstance().getReference("users/${uid}/data")
   // var profileDbUid: String  = ""

    companion object{
        private const val REQUEST_IMAGE_MEDIA = 2
        private const val REQUEST_IMAGE_CAMERA = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)


        val logout = findViewById<ImageView>(R.id.btnLogout)
        val home = findViewById<ImageView>(R.id.btnHome)
        val btnCam = findViewById<ImageView>(R.id.btnCam)
        val btnGalery = findViewById<ImageView>(R.id.btnGalery)
        val btnSave = findViewById<Button>(R.id.btnSave)

        requestPermissions()

        findViewById<EditText>(R.id.email).setText(firebaseAuth.currentUser?.email)

        btnGalery.setOnClickListener(){
            val intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(intent, REQUEST_IMAGE_MEDIA)
        }

        btnCam.setOnClickListener(){
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
        }

        btnSave.setOnClickListener{
            saveProfile()
        }

        logout.setOnClickListener {
            firebaseAuth.signOut()
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

        home.setOnClickListener{
            Navigator.goTo(this, Tasks_main::class.java)
            finish()
        }

        dataBaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())return
                loadDataProfile()
            }
            override fun onCancelled(error: DatabaseError) {}
        })



    }
    fun loadDataProfile(){
        dataBaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())return

                if((snapshot.child("username").value!="") or (snapshot.child("name").value!="")){
                    findViewById<EditText>(R.id.username).setText(snapshot.child("username").value.toString())
                    findViewById<EditText>(R.id.name).setText(snapshot.child("name").value.toString())
                }


                val image = snapshot.child("image").value.toString()

                //transformas texto em base64 byteArray
                val imageUrlDecode = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
                //transformas byteArray em bitmap
                val imageBytToBitmap = android.graphics.BitmapFactory.decodeByteArray(imageUrlDecode, 0, imageUrlDecode.size)
                findViewById<CircleImageView>(R.id.profile_image).setImageBitmap(imageBytToBitmap)

            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
    fun saveProfile(){

        val name = findViewById<EditText>(R.id.name)
        val username = findViewById<EditText>(R.id.username)

        //tranformando imagem em base64 usanso OUTPUTSTREAM(coloca imagem na ram temporariamente para realizar uma atividade)
        val baos =ByteArrayOutputStream()
        this.imageBit?.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val imageData = baos.toByteArray()
        val imageBase64 = android.util.Base64.encodeToString(imageData, android.util.Base64.DEFAULT)

        val dbRefProfileKey = FirebaseDatabase.getInstance().getReference("users/${uid}/data/")
        dbRefProfileKey.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                if(!snapshot.exists())return

                    val profile = snapshot.value as HashMap<String, String>

                    profile["username"] = username.text.toString()
                    profile["name"] = name.text.toString()
                    profile["image"] = imageBase64
                    dbRefProfileKey.setValue(profile)


            }

            override fun onCancelled(error: DatabaseError) {}
        })

//        if(profileDbUid == ""){
//            val profile = hashMapOf(
//                "username" to username,
//                "image" to "",
//                "name" to name
//
//                //imageBit
//
//            )
//            dataBaseRef.push().setValue(profile)
//            Handler(Looper.getMainLooper()).postDelayed({
//                Navigator.goTo(this, Tasks_main::class.java)
//                finish()
//            }, 1500)
//        }else{
//
//        }


        Toast.makeText(this, R.string.profile_saved, Toast.LENGTH_SHORT).show()
        android.os.Handler().postDelayed({
            Navigator.goTo(this, Tasks_main::class.java)
        }, 1500)

    }

    private fun requestPermissions(){
        if(ContextCompat.checkSelfPermission(this@Profile, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@Profile, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
        if(ContextCompat.checkSelfPermission(this@Profile, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@Profile, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_MEDIA)
        }

    }
     //transformar imagem em bitmap
    override fun onActivityResult(
         requestCode: Int,
         resultCode: Int,
         data: Intent?
    ){
        super.onActivityResult(requestCode, resultCode, data)
         val profImage = findViewById<CircleImageView>(R.id.profile_image)
         if(resultCode == RESULT_OK){
                when(requestCode){
                    REQUEST_IMAGE_MEDIA ->{
                        //onde a câmera guarda a imagem temporariamente
                        val selectedImage: Uri? = data?.data
                        if(selectedImage!=null){
                            //imagem em Bitmap
                            val datImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                            profImage.setImageBitmap(datImageBitmap)
                            //setando no app
                            this.imageBit= datImageBitmap
                        }

                    }
                    REQUEST_IMAGE_CAMERA ->{
                        val datImageBitmap = data?.extras?.get("data") as Bitmap
                        profImage.setImageBitmap(datImageBitmap)
                        this.imageBit=null
                    }

                }
         }else{
                Toast.makeText(this, R.string.error_capturrImage, Toast.LENGTH_SHORT).show()
         }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_REQUEST_CAMERA ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_REQUEST_MEDIA ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
