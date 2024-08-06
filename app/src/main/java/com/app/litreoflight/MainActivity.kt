package com.app.litreoflight

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    var Status: Boolean = false

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
            GetStatus { status ->
                Status = !status

                if (isChecked && !status) {
                    FlickSwitch()
                } else if (!isChecked && status) {
                    FlickSwitch()
                }
            }
            AppCompatDelegate.setDefaultNightMode(
                if (Status && isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )

            if (Status && isChecked) {
                bottomNavBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_background
                    )
                )
                bottomNavBar.itemIconTintList =
                    ContextCompat.getColorStateList(this, R.color.dark_icon_tint)
                bottomNavBar.itemTextColor =
                    ContextCompat.getColorStateList(this, R.color.dark_text_tint)
            } else {
                bottomNavBar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                bottomNavBar.itemIconTintList =
                    ContextCompat.getColorStateList(this, R.color.light_icon_tint)
                bottomNavBar.itemTextColor =
                    ContextCompat.getColorStateList(this, R.color.light_text_tint)
            }


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

    fun FlickSwitch()
    {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
                val url = URL("http://192.168.4.1/flickSwitch")
                val json = url.readText()
        }
    }

    fun GetStatus (OnComplete: (Boolean) -> Unit){
        val status = URL("http://192.168.4.1/GetStatus").readText()

        if(status.equals("true")){
            OnComplete(true)
        }else {
            OnComplete(false)
        }
    }

}