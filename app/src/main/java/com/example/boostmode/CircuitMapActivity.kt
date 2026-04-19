package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import kotlin.concurrent.thread

class CircuitMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circuit_map)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val circuitId = intent.getStringExtra("circuit_id") ?: return

        val openDetail = android.view.View.OnClickListener {
            val intent = Intent(this, DetailCircuitActivity::class.java)
            intent.putExtra("circuit_id", circuitId)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.btn_read_more).setOnClickListener(openDetail)
        findViewById<ImageView>(R.id.iv_arrow_right).setOnClickListener(openDetail)

        loadData(circuitId)
    }

    private fun loadData(circuitId: String) {
        thread {
            val db = AppDatabase.getInstance(this)
            val race = db.raceDao().getById(circuitId)
            val circuit = db.circuitDao().getByRaceId(circuitId)

            runOnUiThread {
                race?.let {
                    findViewById<TextView>(R.id.tv_circuit_name).text = it.country.uppercase()
                    findViewById<TextView>(R.id.tv_gp_name).text = "${it.country} GP"
                    findViewById<TextView>(R.id.tv_round).text = "Round ${it.round}"
                    findViewById<TextView>(R.id.tv_dates).text = it.dates
                    findViewById<TextView>(R.id.tv_status).text = it.status
                }

                circuit?.let {
                    findViewById<TextView>(R.id.tv_lap_time).text = it.lapTimeRecord
                    findViewById<TextView>(R.id.tv_turns).text = it.turns
                    findViewById<TextView>(R.id.tv_laps).text = it.laps
                    findViewById<TextView>(R.id.tv_length).text = it.length
                }

                findViewById<ImageView>(R.id.iv_circuit_map).setImageResource(R.drawable.map_monza)
            }
        }
    }
}