package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.DriverEntity
import kotlin.concurrent.thread

class TeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        loadTeams()
    }

    private fun loadTeams() {
        thread {
            val db = AppDatabase.getInstance(this)
            val drivers = db.driverDao().getAll()
            val standings = F1ApiService.getConstructorStandings()
            runOnUiThread { bindTeams(drivers, standings) }
        }
    }

    private fun bindTeams(drivers: List<DriverEntity>, standings: List<F1ApiService.ConstructorStanding>) {
        val container = findViewById<LinearLayout>(R.id.container_teams)
        container.removeAllViews()

        val grouped = drivers.groupBy { it.team }

        val sorted = grouped.entries.sortedWith { a, b ->
            val posA = standings.find { matchTeam(it.name, a.key) }?.position?.toIntOrNull() ?: Int.MAX_VALUE
            val posB = standings.find { matchTeam(it.name, b.key) }?.position?.toIntOrNull() ?: Int.MAX_VALUE
            posA.compareTo(posB)
        }

        sorted.forEach { (team, teamDrivers) ->
            val standing = standings.find { matchTeam(it.name, team) }
            val card = LayoutInflater.from(this).inflate(R.layout.item_team, container, false)

            card.findViewById<TextView>(R.id.tv_team_name).text = team.uppercase()
            card.findViewById<TextView>(R.id.tv_position).text = if (standing != null) "P${standing.position}" else ""
            card.findViewById<TextView>(R.id.tv_points).text = if (standing != null) "${standing.points} pts" else ""

            teamDrivers.getOrNull(0)?.let {
                card.findViewById<TextView>(R.id.tv_d1_first).text = it.firstName
                card.findViewById<TextView>(R.id.tv_d1_last).text = it.lastName.uppercase()
                card.findViewById<TextView>(R.id.tv_d1_number).text = "#${it.number}"
            }

            teamDrivers.getOrNull(1)?.let {
                card.findViewById<TextView>(R.id.tv_d2_first).text = it.firstName
                card.findViewById<TextView>(R.id.tv_d2_last).text = it.lastName.uppercase()
                card.findViewById<TextView>(R.id.tv_d2_number).text = "#${it.number}"
            }

            container.addView(card)
        }
    }

    private fun matchTeam(apiName: String, dbName: String): Boolean {
        val a = apiName.lowercase()
        val b = dbName.lowercase()
        return a.contains(b) || b.contains(a)
    }
}
