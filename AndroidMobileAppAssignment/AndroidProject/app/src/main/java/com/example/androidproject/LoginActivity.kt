package com.example.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var btnCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)

        val listenerLogin = View.OnClickListener { view->
            Log.v("Click", "btnLogin was clicked")
            val txtLoginEmail = findViewById<EditText>(R.id.txtLoginEmail)
            val txtLoginPassword = findViewById<EditText>(R.id.txtLoginPassword)
            val txtErrorMessage = findViewById<TextView>(R.id.txtErrorMessage)

            val email = txtLoginEmail.text.toString()
            val password = txtLoginPassword.text.toString()


            val user_login = JSONObject()
            user_login.put("email", email)
            user_login.put("password", password)
            Log.v("data", user_login.toString())

            val queue = Volley.newRequestQueue(this)
            val url = "https://jwuclasses.com/csis2023/exercise/login/"

            val request = JsonObjectRequest(Request.Method.POST, url, user_login,
                { response ->
                    Log.v("JSONresponse", response.toString())

                    val success: Int = response.getInt("success")
                    if (success == 1) {
                        val token: String = response.getString("token")
                        User.user_token = token
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                    }
                    if (success == 0) {
                        val error_message: String = response.getString("error_message")
                        txtErrorMessage.text = error_message
                    }

                },
                { Log.e("MyActivity", "Volley request failed") })
            request.setShouldCache(false)
            queue.add(request)
        }
        btnLogin.setOnClickListener(listenerLogin)

        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        val listenerCreateAccount = View.OnClickListener { view->
            Log.v("Click", "btnCreateAccount was clicked")
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        btnCreateAccount.setOnClickListener(listenerCreateAccount)
    }
}