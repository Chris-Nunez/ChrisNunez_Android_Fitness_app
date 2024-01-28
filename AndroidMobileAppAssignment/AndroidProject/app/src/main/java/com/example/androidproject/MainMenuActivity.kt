package com.example.androidproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainMenuActivity : AppCompatActivity() {

    lateinit var btnLogActivity: Button
    lateinit var btnSeeActivityHistory: Button
    lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btnLogActivity = findViewById(R.id.btnLogActivity)
        btnSeeActivityHistory = findViewById(R.id.btnSeeActivityHistory)
        btnLogout = findViewById(R.id.btnLogout)

        val txtFirstName = findViewById<TextView>(R.id.txtFirstName)
        val txtLastName = findViewById<TextView>(R.id.txtLastName)
        val txtNumberofCalories = findViewById<TextView>(R.id.txtNumberofCalories)
        val txtMainMenuErrorMessage = findViewById<TextView>(R.id.txtMainMenuErrorMessage)

        val queue = Volley.newRequestQueue(this)
        val url = "https://jwuclasses.com/csis2023/exercise/getaccount/"

        val user_getinfo = JSONObject()
        user_getinfo.put("token", User.user_token)
        Log.v("data", user_getinfo.toString())


        val request = JsonObjectRequest(
            Request.Method.POST, url, user_getinfo,
            { response ->
                val success: Int = response.getInt("success")

                if (success == 1) {
                    val total_calories: Int = response.getInt("total_calories")
                    val total_calories_string = total_calories.toString()
                    val firstname: String = response.getString("firstname")
                    val lastname: String = response.getString("lastname")

                    txtNumberofCalories.text = total_calories_string
                    txtFirstName.text = firstname
                    txtLastName.text = lastname
                }
                if (success == 0){
                    val error_message = response.getString("error_message")
                    txtMainMenuErrorMessage.text = error_message
                }

            },
            { Log.e("MyActivity", "Volley request failed") })
        request.setShouldCache(false)
        queue.add(request)

        val listenerLogActivity = View.OnClickListener { view->
            Log.v("Click", "btnLogActivity was clicked")
            val intent = Intent(this, LogExerciseActivity::class.java)
            startActivity(intent)
        }
        btnLogActivity.setOnClickListener(listenerLogActivity)

        val listenerSeeActivityHistory = View.OnClickListener { view->
            Log.v("Click", "btnSeeActivityHistory was clicked")
            val intent = Intent(this, SeeHistoryActivity::class.java)
            startActivity(intent)
        }
        btnSeeActivityHistory.setOnClickListener(listenerSeeActivityHistory)

        val listenerLogout = View.OnClickListener { view->
            Log.v("Click", "btnLogout was clicked")
            User.user_token = ""
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener(listenerLogout)

    }
}