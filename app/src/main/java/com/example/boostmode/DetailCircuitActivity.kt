package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailCircuitActivity : AppCompatActivity() {

    private var isAboutExpanded = false
    private var isCharacteristicsExpanded = false
    private var isRecordExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_circuit)

        val circuitId = intent.getStringExtra("circuit_id") ?: return
        val data = getCircuitData(circuitId)
        bindData(data)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        setupSection(
            headerId = R.id.header_about,
            contentId = R.id.content_about,
            arrowId = R.id.arrow_about,
            dividerId = R.id.divider_about,
            isExpanded = { isAboutExpanded },
            setExpanded = { isAboutExpanded = it }
        )

        setupSection(
            headerId = R.id.header_characteristics,
            contentId = R.id.content_characteristics,
            arrowId = R.id.arrow_characteristics,
            dividerId = R.id.divider_characteristics,
            isExpanded = { isCharacteristicsExpanded },
            setExpanded = { isCharacteristicsExpanded = it }
        )

        setupSection(
            headerId = R.id.header_record,
            contentId = R.id.content_record,
            arrowId = R.id.arrow_record,
            dividerId = R.id.divider_record,
            isExpanded = { isRecordExpanded },
            setExpanded = { isRecordExpanded = it }
        )
    }

    private fun setupSection(
        headerId: Int,
        contentId: Int,
        arrowId: Int,
        dividerId: Int,
        isExpanded: () -> Boolean,
        setExpanded: (Boolean) -> Unit
    ) {
        val content = findViewById<View>(contentId)
        val arrow = findViewById<TextView>(arrowId)
        val divider = findViewById<View>(dividerId)

        findViewById<LinearLayout>(headerId).setOnClickListener {
            val expanded = !isExpanded()
            setExpanded(expanded)
            content.visibility = if (expanded) View.VISIBLE else View.GONE
            arrow.text = if (expanded) "↑" else "↓"
            divider.setBackgroundColor(
                if (expanded) getColor(R.color.accent_red)
                else getColor(R.color.text_disabled)
            )
        }
    }

    private fun bindData(data: CircuitData) {
        findViewById<ImageView>(R.id.iv_circuit).setImageResource(data.circuitRes)
        findViewById<TextView>(R.id.tv_circuit_name).text = data.name
        findViewById<TextView>(R.id.tv_gp_name).text = data.gpName
        findViewById<TextView>(R.id.content_about).text = data.about
        findViewById<TextView>(R.id.tv_location).text = data.location
        findViewById<TextView>(R.id.tv_opened).text = data.opened
        findViewById<TextView>(R.id.tv_first_f1_gp).text = data.firstF1Gp
        findViewById<TextView>(R.id.tv_turns).text = data.turns
        findViewById<TextView>(R.id.tv_length).text = data.length
        findViewById<TextView>(R.id.tv_laps).text = data.laps
        findViewById<TextView>(R.id.tv_driver).text = data.driver
        findViewById<TextView>(R.id.tv_car).text = data.car
        findViewById<TextView>(R.id.tv_lap_time).text = data.lapTime
        findViewById<TextView>(R.id.tv_year).text = data.year
        findViewById<TextView>(R.id.tv_avg_speed).text = data.avgSpeed
    }

    private fun getCircuitData(circuitId: String): CircuitData {
        return when (circuitId) {
            "monza" -> CircuitData(
                name = "MONZA", gpName = "Italian GP", round = "Round 15",
                dates = "04-06 sep", status = "upcoming", lapTime = "1:18:79",
                turns = "11", length = "5 793 km", laps = "53",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Monza, Italy", opened = "1922", firstF1Gp = "1949",
                about = "The Monza Circuit, officially called the Autodromo Nazionale Monza, is a 5.793 km race track near the city of Monza, north of Milan, in Italy. Built in 1922, it is the oldest in mainland Europe and known as the Temple of Speed.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "250.706 km/h"
            )
            "spa" -> CircuitData(
                name = "SPA", gpName = "Belgian GP", round = "Round 13",
                dates = "25-27 jul", status = "upcoming", lapTime = "1:41:32",
                turns = "19", length = "7 004 km", laps = "44",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Spa, Belgium", opened = "1921", firstF1Gp = "1950",
                about = "Circuit de Spa-Francorchamps is a motor racing circuit located in the Ardennes forest in Belgium. It is widely regarded as one of the greatest racing circuits in the world.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "233.9 km/h"
            )
            "zandvoort" -> CircuitData(
                name = "ZANDVOORT", gpName = "Dutch GP", round = "Round 14",
                dates = "29-31 aug", status = "upcoming", lapTime = "1:11:10",
                turns = "14", length = "4 259 km", laps = "72",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Zandvoort, Netherlands", opened = "1948", firstF1Gp = "1952",
                about = "Circuit Zandvoort is a motorsport venue in the coastal town of Zandvoort in the Netherlands. The circuit is known for its banked corners and passionate Dutch crowd.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "196.8 km/h"
            )
            "austria" -> CircuitData(
                name = "AUSTRIA", gpName = "Austrian GP", round = "Round 11",
                dates = "27-29 jun", status = "upcoming", lapTime = "1:02:94",
                turns = "10", length = "4 318 km", laps = "71",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Spielberg, Austria", opened = "1969", firstF1Gp = "1970",
                about = "The Red Bull Ring is a motorsport venue in Spielberg, Austria. Nestled in the Styrian mountains, it is one of the shortest circuits on the F1 calendar.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "247.3 km/h"
            )
            "monaco" -> CircuitData(
                name = "MONACO", gpName = "Monaco GP", round = "Round 8",
                dates = "22-25 may", status = "upcoming", lapTime = "1:10:26",
                turns = "19", length = "3 337 km", laps = "78",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Monte Carlo, Monaco", opened = "1929", firstF1Gp = "1950",
                about = "The Circuit de Monaco is a street circuit laid out on the city streets of Monte Carlo and La Condamine. It is the most prestigious and glamorous race on the F1 calendar.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "157.2 km/h"
            )
            else -> CircuitData(
                name = "SPAIN", gpName = "Spanish GP", round = "Round 10",
                dates = "30 may-1 jun", status = "upcoming", lapTime = "1:12:70",
                turns = "16", length = "4 675 km", laps = "66",
                mapRes = R.drawable.map_monza, circuitRes = R.drawable.circuit_monza,
                location = "Barcelona, Spain", opened = "1991", firstF1Gp = "1991",
                about = "The Circuit de Barcelona-Catalunya is a motorsport circuit in Montmeló, near Barcelona. It is a technically demanding circuit that is a favourite for pre-season testing.",
                driver = "Max Verstappen", car = "Red Bull RB21", year = "2025",
                avgSpeed = "210.5 km/h"
            )
        }
    }
}