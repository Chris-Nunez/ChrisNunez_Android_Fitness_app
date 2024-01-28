package com.example.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SeeHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_history)

        val btnBackToMainMenu: Button = findViewById(R.id.btnBackToMainMenu)
        val txtSeeActivityErrorMessage = findViewById<TextView>(R.id.txtSeeActivityErrorMessage)

        val data = ArrayList<RecyclerViewMainItem>()

        val recyclerview = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewMainAdapter(data)
        recyclerview.adapter = adapter

        val user_activity_history = JSONObject()
        user_activity_history.put("token", User.user_token)
        Log.v("data", user_activity_history.toString())

        val queue = Volley.newRequestQueue(this)
        val url = "https://jwuclasses.com/csis2023/exercise/getactivityhistory/"

        val request = JsonObjectRequest(
            Request.Method.POST, url, user_activity_history,
            { response ->
                Log.v("JSONresponse", response.toString())

                val success: Int = response.getInt("success")
                if (success == 1) {
                    val activities = response.getJSONArray("activities")
                    val items = mutableListOf<RecyclerViewMainItem>()

                    for (i in 0 until activities.length()) {
                        val activityItem = activities.getJSONObject(i)

                        val activity_name = activityItem.getString("activity_name")
                        val calories = activityItem.getInt("calories")
                        val activity_date = activityItem.getString("activity_date")
                        val hours_duration = activityItem.getString("hours_duration").toFloat()
                        val image = activityItem.getString("image")

                        val recyclerViewMainItem = RecyclerViewMainItem(
                            activity_name,
                            calories,
                            activity_date,
                            hours_duration,
                            image )

                        items.add(recyclerViewMainItem)
                    }
                    adapter.updateList(items)
                }
                if (success == 0){
                    val error_message = response.getString("error_message")
                    txtSeeActivityErrorMessage.text = error_message
                }

            },
            { Log.e("MyActivity", "Volley request failed") })
        request.setShouldCache(false)
        queue.add(request)

        val listenerBackToMainMenu = View.OnClickListener { view->
            Log.v("Click", "btnBackToMainMenu was clicked")
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
        btnBackToMainMenu.setOnClickListener(listenerBackToMainMenu)

    }
}