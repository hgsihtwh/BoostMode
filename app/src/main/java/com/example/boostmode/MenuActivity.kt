package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<TextView>(R.id.btn_close).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.menu_map).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        // TODO: добавить RaceCalendarActivity
        // findViewById<TextView>(R.id.menu_calendar).setOnClickListener {
        //     startActivity(Intent(this, RaceCalendarActivity::class.java))
        // }
    }
}