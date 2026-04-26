package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.CircuitEntity
import com.example.boostmode.database.entity.RaceEntity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import kotlin.concurrent.thread

class CircuitMapActivity : AppCompatActivity() {

    private lateinit var circuitMap: MapView

    companion object {
        private val coordinates = mapOf(
            "australia"  to GeoPoint(-37.8497, 144.9680),
            "china"      to GeoPoint(31.3389,  121.2198),
            "japan"      to GeoPoint(34.8431,  136.5407),
            "miami"      to GeoPoint(25.9581,  -80.2389),
            "canada"     to GeoPoint(45.5000,  -73.5228),
            "monaco"     to GeoPoint(43.7347,    7.4205),
            "barcelona"  to GeoPoint(41.5700,    2.2611),
            "madrid"     to GeoPoint(40.4168,   -3.7038),
            "britain"    to GeoPoint(52.0786,   -1.0169),
            "belgium"    to GeoPoint(50.4372,    5.9714),
            "hungary"    to GeoPoint(47.5789,   19.2486),
            "netherlands" to GeoPoint(52.3888,   4.5409),
            "italy"      to GeoPoint(45.6156,    9.2811),
            "azerbaijan" to GeoPoint(40.3725,   49.8533),
            "singapore"  to GeoPoint(1.2914,   103.8640),
            "usa"        to GeoPoint(30.1328,  -97.6411),
            "mexico"     to GeoPoint(19.4042,  -99.0907),
            "brazil"     to GeoPoint(-23.7036, -46.6997),
            "lasvegas"   to GeoPoint(36.1147,  -115.1728),
            "qatar"      to GeoPoint(25.4900,   51.4536),
            "abudhabi"   to GeoPoint(24.4672,   54.6031)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_circuit_map)

        val circuitId = intent.getStringExtra("circuit_id") ?: return

        circuitMap = findViewById(R.id.iv_circuit_map)
        setupMap(circuitId)

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

    private fun setupMap(circuitId: String) {
        circuitMap.setTileSource(
            XYTileSource(
                "CartoDB.DarkMatter", 0, 19, 256, ".png",
                arrayOf(
                    "https://a.basemaps.cartocdn.com/dark_all/",
                    "https://b.basemaps.cartocdn.com/dark_all/",
                    "https://c.basemaps.cartocdn.com/dark_all/"
                ),
                "© OpenStreetMap contributors © CartoDB"
            )
        )
        circuitMap.setMultiTouchControls(false)
        circuitMap.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
        circuitMap.isFocusable = false
        circuitMap.isClickable = false

        val point = coordinates[circuitId] ?: GeoPoint(0.0, 0.0)
        circuitMap.controller.setZoom(14.0)
        circuitMap.controller.setCenter(point)
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

    override fun onResume() {
        super.onResume()
        circuitMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        circuitMap.onPause()
    }
}
