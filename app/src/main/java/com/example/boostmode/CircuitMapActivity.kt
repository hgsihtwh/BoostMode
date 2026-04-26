package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.CircuitEntity
import com.example.boostmode.database.entity.RaceEntity
import kotlin.concurrent.thread

class CircuitMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circuit_map)

        val circuitId = intent.getStringExtra("circuit_id") ?: return

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val openDetail = android.view.View.OnClickListener {
            startActivity(
                Intent(this, DetailCircuitActivity::class.java)
                    .putExtra("circuit_id", circuitId)
            )
        }
        findViewById<TextView>(R.id.btn_read_more).setOnClickListener(openDetail)
        findViewById<ImageView>(R.id.iv_arrow_right).setOnClickListener(openDetail)

        loadCircuit(circuitId)
    }

    private fun loadCircuit(circuitId: String) {
        thread {
            val db = AppDatabase.getInstance(this)
            val circuit = db.circuitDao().getByRaceId(circuitId)
            val race = db.raceDao().getById(circuitId)
            runOnUiThread {
                if (circuit != null && race != null) {
                    bindData(circuit, race)
                }
            }
        }
    }

    private fun bindData(circuit: CircuitEntity, race: RaceEntity) {
        findViewById<ImageView>(R.id.iv_circuit_map).setImageResource(R.drawable.map_monza)
        findViewById<TextView>(R.id.tv_circuit_name).text = race.city.uppercase()
        findViewById<TextView>(R.id.tv_gp_name).text = "${race.country} GP"
        findViewById<TextView>(R.id.tv_round).text = "Round ${race.round}"
        findViewById<TextView>(R.id.tv_dates).text = race.dates
        findViewById<TextView>(R.id.tv_status).text = race.status
        findViewById<TextView>(R.id.tv_lap_time).text = circuit.lapTimeRecord
        findViewById<TextView>(R.id.tv_turns).text = circuit.turns
        findViewById<TextView>(R.id.tv_laps).text = circuit.laps
        findViewById<TextView>(R.id.tv_length).text = circuit.length
    }
}
