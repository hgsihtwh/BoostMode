package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.RaceEntity
import kotlin.concurrent.thread

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

        loadRaces()
    }

    private fun loadRaces() {
        thread {
            val db = AppDatabase.getInstance(this)
            val races = db.raceDao().getAll()
            runOnUiThread {
                bindRaces(races)
            }
        }
    }

    private fun bindRaces(races: List<RaceEntity>) {
        val previous = races.filter { it.status == "previous" }
        val current = races.filter { it.status == "current" }
        val upcoming = races.filter { it.status == "upcoming" }

        val containerPrevious = findViewById<LinearLayout>(R.id.container_previous)
        val containerCurrent = findViewById<LinearLayout>(R.id.container_current)
        val containerUpcoming = findViewById<LinearLayout>(R.id.container_upcoming)

        containerPrevious.removeAllViews()
        containerCurrent.removeAllViews()
        containerUpcoming.removeAllViews()

        previous.forEach { race ->
            val card = createRaceCard(race)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 12.dpToPx()
            card.layoutParams = params
            containerPrevious.addView(card)
        }

        current.forEach { race ->
            val card = createRaceCard(race)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 12.dpToPx()
            card.layoutParams = params
            containerCurrent.addView(card)
        }

        upcoming.forEach { race ->
            val card = createRaceCard(race)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 12.dpToPx()
            card.layoutParams = params
            containerUpcoming.addView(card)
        }

        setupToggle(R.id.btn_toggle_previous, containerPrevious, { isPreviousExpanded }, { isPreviousExpanded = it })
        setupToggle(R.id.btn_toggle_current, containerCurrent, { isCurrentExpanded }, { isCurrentExpanded = it })
        setupToggle(R.id.btn_toggle_upcoming, containerUpcoming, { isUpcomingExpanded }, { isUpcomingExpanded = it })
    }

    private fun createRaceCard(race: RaceEntity): View {
        val card = LayoutInflater.from(this)
            .inflate(R.layout.item_race, null, false)

        card.findViewById<TextView>(R.id.tv_round_number).text = race.round
        card.findViewById<TextView>(R.id.tv_country).text = race.country
        card.findViewById<TextView>(R.id.tv_city).text = race.city
        card.findViewById<TextView>(R.id.tv_dates).text = race.dates

        val openPredictions = View.OnClickListener {
            val intent = Intent(this, MyPredictionsActivity::class.java)
            intent.putExtra("race_id", race.id)
            startActivity(intent)
        }

        card.findViewById<TextView>(R.id.tv_my_prediction).setOnClickListener(openPredictions)
        card.findViewById<ImageView>(R.id.iv_prediction_arrow).setOnClickListener(openPredictions)

        val openSchedule = View.OnClickListener {
            val intent = Intent(this, RaceScheduleActivity::class.java)
            intent.putExtra("schedule_id", race.id)
            startActivity(intent)
        }

        card.findViewById<TextView>(R.id.tv_schedule).setOnClickListener(openSchedule)
        card.findViewById<ImageView>(R.id.iv_schedule_arrow).setOnClickListener(openSchedule)

        return card
    }

    private fun setupToggle(
        btnId: Int,
        container: LinearLayout,
        isExpanded: () -> Boolean,
        setExpanded: (Boolean) -> Unit
    ) {
        val btn = findViewById<TextView>(btnId)
        btn.setOnClickListener {
            val expanded = !isExpanded()
            setExpanded(expanded)
            container.visibility = if (expanded) View.VISIBLE else View.GONE
            btn.text = if (expanded) "↓" else "↑"
        }
    }

    private fun Int.dpToPx(): Int =
        (this * resources.displayMetrics.density).toInt()
}