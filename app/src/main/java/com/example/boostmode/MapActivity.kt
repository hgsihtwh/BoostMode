package com.example.boostmode

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView

    data class CircuitMarker(
        val country: String,
        val city: String,
        val round: String,
        val lat: Double,
        val lon: Double,
        val circuitId: String? = null
    )

    private val circuits = listOf(
        CircuitMarker("Australia", "Melbourne", "01", -37.8497, 144.9680, "australia"),
        CircuitMarker("China", "Shanghai", "02", 31.3389, 121.2198, "china"),
        CircuitMarker("Japan", "Suzuka", "03", 34.8431, 136.5407, "japan"),
        CircuitMarker("Miami", "Miami", "04", 25.9581, -80.2389, "miami"),
        CircuitMarker("Canada", "Montreal", "05", 45.5000, -73.5228, "canada"),
        CircuitMarker("Monaco", "Monte Carlo", "06", 43.7347, 7.4205, "monaco"),
        CircuitMarker("Spain", "Barcelona", "07", 41.5700, 2.2611, "barcelona"),
        CircuitMarker("Spain", "Madrid", "08", 40.4168, -3.7038, "madrid"),
        CircuitMarker("Great Britain", "Silverstone", "09", 52.0786, -1.0169, "britain"),
        CircuitMarker("Belgium", "Spa", "10", 50.4372, 5.9714, "belgium"),
        CircuitMarker("Hungary", "Budapest", "11", 47.5789, 19.2486, "hungary"),
        CircuitMarker("Netherlands", "Zandvoort", "12", 52.3888, 4.5409, "netherlands"),
        CircuitMarker("Italy", "Monza", "13", 45.6156, 9.2811, "italy"),
        CircuitMarker("Azerbaijan", "Baku", "14", 40.3725, 49.8533, "azerbaijan"),
        CircuitMarker("Singapore", "Singapore", "15", 1.2914, 103.8640, "singapore"),
        CircuitMarker("USA", "Austin", "16", 30.1328, -97.6411, "usa"),
        CircuitMarker("Mexico", "Mexico City", "17", 19.4042, -99.0907, "mexico"),
        CircuitMarker("Brazil", "São Paulo", "18", -23.7036, -46.6997, "brazil"),
        CircuitMarker("Las Vegas", "Las Vegas", "19", 36.1147, -115.1728, "lasvegas"),
        CircuitMarker("Qatar", "Lusail", "20", 25.4900, 51.4536, "qatar"),
        CircuitMarker("Abu Dhabi", "Yas Marina", "21", 24.4672, 54.6031, "abudhabi")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map_view)
        map.setTileSource(
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
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
        map.isHorizontalMapRepetitionEnabled = false
        map.isVerticalMapRepetitionEnabled = false
        map.setScrollableAreaLimitDouble(BoundingBox(85.0, 180.0, -85.0, -180.0))
        map.minZoomLevel = 2.0
        map.post {
            map.controller.setZoom(2.5)
            map.controller.setCenter(GeoPoint(20.0, 20.0))
        }

        addMarkers()

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun smallMarkerIcon(): BitmapDrawable {
        val original = BitmapFactory.decodeResource(resources, R.drawable.ic_marker_white)
        val scaled = Bitmap.createScaledBitmap(
            original,
            original.width / 6,
            original.height / 6,
            true
        )
        return BitmapDrawable(resources, scaled)
    }

    private fun addMarkers() {
        val markerIcon = smallMarkerIcon()

        circuits.forEach { circuit ->
            val marker = Marker(map)
            marker.position = GeoPoint(circuit.lat, circuit.lon)
            marker.title = "${circuit.country} GP"
            marker.snippet = "Round ${circuit.round} · ${circuit.city}"
            marker.icon = markerIcon
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.infoWindow = CircuitInfoWindow(map, this, circuit.circuitId)
            marker.setOnMarkerClickListener { m, _ ->
                m.showInfoWindow()
                true
            }
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

    inner class CircuitInfoWindow(
        mapView: MapView,
        private val context: Context,
        private val circuitId: String?
    ) : InfoWindow(R.layout.map_info_window, mapView) {

        override fun onOpen(item: Any?) {
            val marker = item as Marker
            mView.findViewById<TextView>(R.id.tv_iw_title).text = marker.title
            mView.findViewById<TextView>(R.id.tv_iw_snippet).text = marker.snippet
            mView.setOnClickListener {
                if (circuitId != null) {
                    context.startActivity(
                        Intent(context, CircuitMapActivity::class.java)
                            .putExtra("circuit_id", circuitId)
                    )
                }
                close()
            }
        }

        override fun onClose() {}
    }
}
