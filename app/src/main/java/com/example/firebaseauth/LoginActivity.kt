package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
        login()
    }

    private fun login(){
        loginButton.setOnClickListener {
            if(TextUtils.isEmpty(login_usernameInput.text.toString())){
                login_usernameInput.error = "Please enter username"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(login_passwordInput.text.toString()))
            {
                login_passwordInput.error = "Please enter password"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(login_usernameInput.text.toString(),login_passwordInput.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this,ProfileActivity::class.java))
                        finish()
                    }else
                    {
                        Toast.makeText(this,"Login Failed, please try again", Toast.LENGTH_SHORT).show()

                    }
                }
        }

        registerText.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

        }
    }
}