package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CircuitMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circuit_map)

        val circuitId = intent.getStringExtra("circuit_id") ?: return

        val data = getCircuitData(circuitId)
        bindData(data)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        // TODO: добавить DetailCircuitActivity
        // findViewById<TextView>(R.id.btn_read_more).setOnClickListener {
        //     val intent = Intent(this, DetailCircuitActivity::class.java)
        //     intent.putExtra("circuit_id", circuitId)
        //     startActivity(intent)
        // }
    }

    private fun bindData(data: CircuitData) {
        findViewById<ImageView>(R.id.iv_circuit_map).setImageResource(data.mapRes)
        findViewById<TextView>(R.id.tv_circuit_name).text = data.name
        findViewById<TextView>(R.id.tv_gp_name).text = data.gpName
        findViewById<TextView>(R.id.tv_round).text = data.round
        findViewById<TextView>(R.id.tv_dates).text = data.dates
        findViewById<TextView>(R.id.tv_status).text = data.status
        findViewById<TextView>(R.id.tv_lap_time).text = data.lapTime
        findViewById<TextView>(R.id.tv_turns).text = data.turns
        findViewById<TextView>(R.id.tv_length).text = data.length
    }

    private fun getCircuitData(circuitId: String): CircuitData {
        return when (circuitId) {
            "monza" -> CircuitData(
                name = "MONZA",
                gpName = "Italian GP",
                round = "Round 15",
                dates = "04-06 sep",
                status = "upcoming",
                lapTime = "1:18:79",
                turns = "11",
                length = "5 793 km",
                mapRes = R.drawable.map_monza
            )
            "spa" -> CircuitData(
                name = "SPA",
                gpName = "Belgian GP",
                round = "Round 13",
                dates = "25-27 jul",
                status = "upcoming",
                lapTime = "1:41:32",
                turns = "19",
                length = "7 004 km",
                mapRes = R.drawable.map_monza
            )
            "zandvoort" -> CircuitData(
                name = "ZANDVOORT",
                gpName = "Dutch GP",
                round = "Round 14",
                dates = "29-31 aug",
                status = "upcoming",
                lapTime = "1:11:10",
                turns = "14",
                length = "4 259 km",
                mapRes = R.drawable.map_monza
            )
            "austria" -> CircuitData(
                name = "AUSTRIA",
                gpName = "Austrian GP",
                round = "Round 11",
                dates = "27-29 jun",
                status = "upcoming",
                lapTime = "1:02:94",
                turns = "10",
                length = "4 318 km",
                mapRes = R.drawable.map_monza
            )
            "monaco" -> CircuitData(
                name = "MONACO",
                gpName = "Monaco GP",
                round = "Round 8",
                dates = "22-25 may",
                status = "upcoming",
                lapTime = "1:10:26",
                turns = "19",
                length = "3 337 km",
                mapRes = R.drawable.map_monza
            )
            else -> CircuitData(
                name = "SPAIN",
                gpName = "Spanish GP",
                round = "Round 10",
                dates = "30 may-1 jun",
                status = "upcoming",
                lapTime = "1:12:70",
                turns = "16",
                length = "4 675 km",
                mapRes = R.drawable.map_monza
            )
        }
    }
}