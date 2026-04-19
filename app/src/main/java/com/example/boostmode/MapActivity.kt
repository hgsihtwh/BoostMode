package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val markers = mapOf(
            R.id.marker_monza to "monza",
            R.id.marker_spa to "spa",
            R.id.marker_zandvoort to "zandvoort",
            R.id.marker_austria to "austria",
            R.id.marker_monaco to "monaco",
            R.id.marker_spain to "spain"
        )

        markers.forEach { (viewId, circuitId) ->
            findViewById<ImageView>(viewId).setOnClickListener {
                val intent = Intent(this, CircuitMapActivity::class.java)
                intent.putExtra("circuit_id", circuitId)
                startActivity(intent)
            }
        }
    }
}