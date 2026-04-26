package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import kotlin.concurrent.thread

class DetailCircuitActivity : AppCompatActivity() {

    private var isAboutExpanded = false
    private var isCharacteristicsExpanded = false
    private var isRecordExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_circuit)

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

        val circuitId = intent.getStringExtra("circuit_id") ?: return
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
                }

                circuit?.let {
                    findViewById<ImageView>(R.id.iv_circuit).setImageResource(R.drawable.circuit_monza)
                    findViewById<TextView>(R.id.content_about).text = it.about
                    findViewById<TextView>(R.id.tv_location).text = it.location
                    findViewById<TextView>(R.id.tv_opened).text = it.opened
                    findViewById<TextView>(R.id.tv_first_f1_gp).text = it.firstF1Gp
                    findViewById<TextView>(R.id.tv_turns).text = it.turns
                    findViewById<TextView>(R.id.tv_length).text = it.length
                    findViewById<TextView>(R.id.tv_laps).text = it.laps
                    findViewById<TextView>(R.id.tv_driver).text = it.driver
                    findViewById<TextView>(R.id.tv_car).text = it.car
                    findViewById<TextView>(R.id.tv_lap_time).text = it.lapTimeRecord
                    findViewById<TextView>(R.id.tv_year).text = it.year
                    findViewById<TextView>(R.id.tv_avg_speed).text = it.avgSpeed
                }
            }
        }
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
}