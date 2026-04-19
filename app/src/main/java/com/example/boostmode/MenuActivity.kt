package com.example.boostmode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<android.widget.TextView>(R.id.btn_close).setOnClickListener {
            finish()
        }
    }
}