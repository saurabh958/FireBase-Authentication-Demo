package com.example.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance("https://fir-authenticationdemo-8553c-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = database?.reference!!.child("profile")

        register()

    }

    private fun register(){

        registerButton.setOnClickListener {

            if(TextUtils.isEmpty(firstnameInput.text.toString())){
                firstnameInput.error = "Please enter first name"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(lastnameInput.text.toString()))
            {
                lastnameInput.error = "Please enter last name"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(usernameInput.text.toString()))
            {
                usernameInput.error = "Please enter user name"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passwordInput.text.toString()))
            {
                passwordInput.error = "Please enter password"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(usernameInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                        currentUSerDb?.child("firstname")?.setValue(firstnameInput.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(lastnameInput.text.toString())

                        Toast.makeText(this, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }

        }

    }
}