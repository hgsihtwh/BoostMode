package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView

    data class CircuitMarker(
        val country: String,
        val city: String,
        val round: String,
        val lat: Double,
        val lon: Double
    )

    private val circuits = listOf(
        CircuitMarker("Australia", "Melbourne", "01", -37.8497, 144.9680),
        CircuitMarker("China", "Shanghai", "02", 31.3389, 121.2198),
        CircuitMarker("Japan", "Suzuka", "03", 34.8431, 136.5407),
        CircuitMarker("Miami", "Miami", "04", 25.9581, -80.2389),
        CircuitMarker("Canada", "Montreal", "05", 45.5000, -73.5228),
        CircuitMarker("Monaco", "Monte Carlo", "06", 43.7347, 7.4205),
        CircuitMarker("Spain", "Barcelona", "07", 41.5700, 2.2611),
        CircuitMarker("Spain", "Madrid", "08", 40.4168, -3.7038),
        CircuitMarker("Great Britain", "Silverstone", "09", 52.0786, -1.0169),
        CircuitMarker("Belgium", "Spa", "10", 50.4372, 5.9714),
        CircuitMarker("Hungary", "Budapest", "11", 47.5789, 19.2486),
        CircuitMarker("Netherlands", "Zandvoort", "12", 52.3888, 4.5409),
        CircuitMarker("Italy", "Monza", "13", 45.6156, 9.2811),
        CircuitMarker("Azerbaijan", "Baku", "14", 40.3725, 49.8533),
        CircuitMarker("Singapore", "Singapore", "15", 1.2914, 103.8640),
        CircuitMarker("USA", "Austin", "16", 30.1328, -97.6411),
        CircuitMarker("Mexico", "Mexico City", "17", 19.4042, -99.0907),
        CircuitMarker("Brazil", "São Paulo", "18", -23.7036, -46.6997),
        CircuitMarker("Las Vegas", "Las Vegas", "19", 36.1147, -115.1728),
        CircuitMarker("Qatar", "Lusail", "20", 25.4900, 51.4536),
        CircuitMarker("Abu Dhabi", "Yas Marina", "21", 24.4672, 54.6031)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map_view)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        map.controller.setZoom(2.5)
        map.controller.setCenter(GeoPoint(20.0, 20.0))

        addMarkers()

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun addMarkers() {
        circuits.forEach { circuit ->
            val marker = Marker(map)
            marker.position = GeoPoint(circuit.lat, circuit.lon)
            marker.title = "${circuit.country} GP"
            marker.snippet = "Round ${circuit.round} · ${circuit.city}"
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)
        }
        map.invalidate()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
