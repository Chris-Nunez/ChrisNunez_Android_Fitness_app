package com.example.androidproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity() {

    lateinit var btnSignUp: Button
    lateinit var btnBackToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnSignUp = findViewById(R.id.btnSignUp)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)

        val listenerSignUp = View.OnClickListener { view->
            Log.v("Click", "btnSignUp was clicked")
            val txtRegisterEmail = findViewById<EditText>(R.id.txtRegisterEmail)
            val txtRegisterPassword = findViewById<EditText>(R.id.txtRegisterPassword)
            val txtRegisterFirstName = findViewById<EditText>(R.id.txtRegisterFirstName)
            val txtRegisterLastName = findViewById<EditText>(R.id.txtRegisterLastName)

            val txtErrorMessageSignUp = findViewById<TextView>(R.id.txtErrorMessageSignUp)

            val email = txtRegisterEmail.text.toString()
            val password = txtRegisterPassword.text.toString()
            val firstname = txtRegisterFirstName.text.toString()
            val lastname = txtRegisterLastName.text.toString()

            val user_signup = JSONObject()
            user_signup.put("email", email)
            user_signup.put("password", password)
            user_signup.put("firstname", firstname)
            user_signup.put("lastname", lastname)
            Log.v("data", User.toString())

            val queue = Volley.newRequestQueue(this)
            val url = "https://jwuclasses.com/csis2023/exercise/register/"

            val request = JsonObjectRequest(
                Request.Method.POST, url, user_signup,
                { response ->
                    Log.v("JSONresponse", response.toString())

                    val success: Int = response.getInt("success")
                    if (success == 1) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    if (success == 0) {
                        val error_message: String = response.getString("error_message")
                        txtErrorMessageSignUp.text = error_message
                    }

                },
                { Log.e("MyActivity", "Volley request failed") })
            request.setShouldCache(false)
            queue.add(request)
        }
        btnSignUp.setOnClickListener(listenerSignUp)

        btnBackToLogin = findViewById(R.id.btnBackToLogin)
        val listenerBackToLogin = View.OnClickListener { view->
            Log.v("Click", "btnBackToLogin was clicked")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnBackToLogin.setOnClickListener(listenerBackToLogin)

    }
}
