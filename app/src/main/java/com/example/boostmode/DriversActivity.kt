package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.DriverEntity
import kotlin.concurrent.thread

class DriversActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        loadDrivers()
    }

    private fun loadDrivers() {
        thread {
            val db = AppDatabase.getInstance(this)
            val drivers = db.driverDao().getAll()
            runOnUiThread {
                bindDrivers(drivers)
            }
        }
    }

    private fun bindDrivers(drivers: List<DriverEntity>) {
        val container = findViewById<LinearLayout>(R.id.container_drivers)
        container.removeAllViews()

        val grouped = drivers.groupBy { it.team }

        grouped.forEach { (team, teamDrivers) ->
            val teamLabel = TextView(this).apply {
                text = team
                textSize = 12f
                setTextColor(ContextCompat.getColor(this@DriversActivity, R.color.accent_red))
                typeface = resources.getFont(R.font.formula1_regular)
                setPadding(0, 24, 0, 8)
            }
            container.addView(teamLabel)

            teamDrivers.forEach { driver ->
                val row = LayoutInflater.from(this)
                    .inflate(R.layout.item_driver, container, false)

                row.findViewById<TextView>(R.id.tv_first_name).text = driver.firstName
                row.findViewById<TextView>(R.id.tv_last_name).text = driver.lastName
                row.findViewById<TextView>(R.id.tv_number).text = "/ ${driver.number}"

                container.addView(row)
            }
        }
    }
}