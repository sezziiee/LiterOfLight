package com.app.litreoflight

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        val switch1 : Switch= findViewById(R.id.mainSwitch)
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            if (isChecked) {
                bottomNavBar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_background))
                bottomNavBar.itemIconTintList = ContextCompat.getColorStateList(this, R.color.dark_icon_tint)
                bottomNavBar.itemTextColor = ContextCompat.getColorStateList(this, R.color.dark_text_tint)
            } else {
                bottomNavBar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                bottomNavBar.itemIconTintList = ContextCompat.getColorStateList(this, R.color.light_icon_tint)
                bottomNavBar.itemTextColor = ContextCompat.getColorStateList(this, R.color.light_text_tint)
            }

            Read()
        }

        bottomNavBar.setSelectedItemId(R.id.ic_home)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_Schedule -> {
                    startActivity(Intent(this, MeActivity::class.java))
                    true
                }
                R.id.ic_Me -> {
                    startActivity(Intent(this, MeActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    private var isDarkTheme = false

    private fun toggleTheme() {
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Dark)

        } else {
            setTheme(R.style.AppTheme_Light)
        }
        recreate() // Recreate the activity to apply the new theme
        isDarkTheme = !isDarkTheme
    }

    fun Read()
    {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {

            //try{
                val url = URL("http://192.168.4.1/flickSwitch")
                val json = url.readText()


                Handler(Looper.getMainLooper()).post {
                    Log.d("AddNewUser", "Plain Json Vars:" + json.toString())
                    //Log.d("AddNewUser", "Converted Json:" + userList.toString())
                    //var Text = findViewById<TextView>(R.id.txtOutput)
                   // Text.setText(userList.toString())

                }


          //  }
            /*catch (e:Exception){
                Log.d("AddNewUser", "Error: "+ e.toString())
                var Text = findViewById<TextView>(R.id.txtOutput)
                Text.setText("Error:" + e.toString())
            }*/
        }
    }
}