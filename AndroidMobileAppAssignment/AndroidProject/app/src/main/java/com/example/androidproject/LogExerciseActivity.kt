package com.example.androidproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LogExerciseActivity : AppCompatActivity() {

    lateinit var btnLogAnActivity: Button
    lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_exercise)

        btnLogAnActivity = findViewById(R.id.btnLogAnActivity)
        val txtinputHoursInput = findViewById<EditText>(R.id.txtinputHoursInput)
        val txtLogActivityErrorMessage = findViewById<TextView>(R.id.txtLogActivityErrorMessage)
        val radiogroupActivityName = findViewById<RadioGroup>(R.id.radiogroupActivityName)
        var selectedRadioButton = ""

        radiogroupActivityName.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radiobuttonRunning -> {

                    selectedRadioButton = "running"
                }

                R.id.radiobuttonWalking -> {

                    selectedRadioButton = "walking"
                }

                R.id.radiobuttonSwimming -> {

                    selectedRadioButton = "swimming"
                }
            }
        }

        val listenerLogAnActivity = View.OnClickListener { view ->
            Log.v("Click", "btnLogAnActivity was clicked")

            var hours_duration = txtinputHoursInput.text.toString()

            // If the user leaves the hours blank, make the duration default to 0
            // Otherwise, keep it as the user inputted
            if (hours_duration == "") {
                hours_duration = "0"
                hours_duration.toFloat()
            }
            else {
                hours_duration.toFloat()
            }

            val user_activity = JSONObject()
            user_activity.put("token", User.user_token)
            user_activity.put("activity_name", selectedRadioButton)
            user_activity.put("hours_duration", hours_duration)
            Log.v("data", user_activity.toString())

            val queue = Volley.newRequestQueue(this)
            val url = "https://jwuclasses.com/csis2023/exercise/saveactivity/"

            val request = JsonObjectRequest(Request.Method.POST, url, user_activity,
                { response ->
                    Log.v("JSONresponse", response.toString())

                    val success: Int = response.getInt("success")
                    if (success == 1) {
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                    }
                    if (success == 0){
                        val error_message = response.getString("error_message")
                        txtLogActivityErrorMessage.text = error_message
                    }
                },
                { Log.e("MyActivity", "Volley request failed") })
            request.setShouldCache(false)
            queue.add(request)
        }
        btnLogAnActivity.setOnClickListener(listenerLogAnActivity)

        btnCancel = findViewById(R.id.btnCancel)
        val listenerCancel = View.OnClickListener { view->
            Log.v("Click", "btnBackToLogin was clicked")
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener(listenerCancel)

    }
}