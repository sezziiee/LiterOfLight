package com.app.litreoflight

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun Read()
    {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try{
                val url = URL("https://opsc20240710154110.azurewebsites.net/GetAllLoans")
                val json = url.readText()


                Handler(Looper.getMainLooper()).post {
                    Log.d("AddNewUser", "Plain Json Vars:" + json.toString())
                    //Log.d("AddNewUser", "Converted Json:" + userList.toString())
                    var Text = findViewById<TextView>(R.id.txtOutput)
                   // Text.setText(userList.toString())
                }


            }
            catch (e:Exception){
                Log.d("AddNewUser", "Error: "+ e.toString())
                var Text = findViewById<TextView>(R.id.txtOutput)
                Text.setText("Error:" + e.toString())
            }
        }
    }
}