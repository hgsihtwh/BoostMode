package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RaceCalendarActivity : AppCompatActivity() {

    private var isPreviousExpanded = true
    private var isCurrentExpanded = true
    private var isUpcomingExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_race_calendar)

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        setupToggle(
            btnId = R.id.btn_toggle_previous,
            containerId = R.id.container_previous,
            isExpanded = { isPreviousExpanded },
            setExpanded = { isPreviousExpanded = it }
        )

        setupToggle(
            btnId = R.id.btn_toggle_current,
            containerId = R.id.container_current,
            isExpanded = { isCurrentExpanded },
            setExpanded = { isCurrentExpanded = it }
        )

        setupToggle(
            btnId = R.id.btn_toggle_upcoming,
            containerId = R.id.container_upcoming,
            isExpanded = { isUpcomingExpanded },
            setExpanded = { isUpcomingExpanded = it }
        )

        bindRace(
            viewId = R.id.race_australia,
            country = "Australia",
            city = "Melbourne",
            round = "01",
            dates = "06-08 mar",
            scheduleId = "australia"
        )

        bindRace(
            viewId = R.id.race_china,
            country = "China",
            city = "Shanghai",
            round = "02",
            dates = "13-15 mar",
            scheduleId = "china"
        )

        bindRace(
            viewId = R.id.race_japan,
            country = "Japan",
            city = "Suzuka",
            round = "03",
            dates = "27-29 mar",
            scheduleId = "japan"
        )

        bindRace(
            viewId = R.id.race_bahrain,
            country = "Bahrain",
            city = "Sakhir",
            round = "04",
            dates = "10-12 apr",
            scheduleId = "bahrain"
        )

        bindRace(
            viewId = R.id.race_saudi,
            country = "Saudi Arabia",
            city = "Jeddah",
            round = "05",
            dates = "17-19 apr",
            scheduleId = "saudi"
        )

        bindRace(
            viewId = R.id.race_miami,
            country = "Miami",
            city = "Miami",
            round = "06",
            dates = "01-03 may",
            scheduleId = "miami"
        )
    }

    private fun setupToggle(
        btnId: Int,
        containerId: Int,
        isExpanded: () -> Boolean,
        setExpanded: (Boolean) -> Unit
    ) {
        val btn = findViewById<TextView>(btnId)
        val container = findViewById<LinearLayout>(containerId)

        btn.setOnClickListener {
            val expanded = !isExpanded()
            setExpanded(expanded)
            container.visibility = if (expanded) View.VISIBLE else View.GONE
            btn.text = if (expanded) "↓" else "↑"
        }
    }

    private fun bindRace(
        viewId: Int,
        country: String,
        city: String,
        round: String,
        dates: String,
        scheduleId: String
    ) {
        val card = findViewById<View>(viewId)
        card.findViewById<TextView>(R.id.tv_country).text = country
        card.findViewById<TextView>(R.id.tv_city).text = city
        card.findViewById<TextView>(R.id.tv_round_number).text = round
        card.findViewById<TextView>(R.id.tv_dates).text = dates

        card.findViewById<TextView>(R.id.tv_schedule).setOnClickListener {
            val intent = Intent(this, RaceScheduleActivity::class.java)
            intent.putExtra("schedule_id", scheduleId)
            startActivity(intent)
        }

        card.findViewById<ImageView>(R.id.iv_schedule_arrow).setOnClickListener {
            val intent = Intent(this, RaceScheduleActivity::class.java)
            intent.putExtra("schedule_id", scheduleId)
            startActivity(intent)
        }
    }
}