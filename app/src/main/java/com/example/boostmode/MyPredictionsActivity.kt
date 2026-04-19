package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.PredictionEntity
import com.example.boostmode.database.entity.RaceEntity
import kotlin.concurrent.thread

class MyPredictionsActivity : AppCompatActivity() {

    private var isPreviousExpanded = true
    private var isCurrentExpanded = true
    private var isUpcomingExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_predictions)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        setupToggle(R.id.btn_toggle_previous, R.id.container_previous, { isPreviousExpanded }, { isPreviousExpanded = it })
        setupToggle(R.id.btn_toggle_current, R.id.container_current, { isCurrentExpanded }, { isCurrentExpanded = it })
        setupToggle(R.id.btn_toggle_upcoming, R.id.container_upcoming, { isUpcomingExpanded }, { isUpcomingExpanded = it })

        loadPredictions()
    }

    override fun onResume() {
        super.onResume()
        loadPredictions()
    }

    private fun loadPredictions() {
        thread {
            val db = AppDatabase.getInstance(this)
            val races = db.raceDao().getAll()
            val predictions = db.predictionDao().getAll()
            runOnUiThread {
                bindPredictions(races, predictions)
            }
        }
    }

    private fun bindPredictions(races: List<RaceEntity>, predictions: List<PredictionEntity>) {
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
            containerPrevious.addView(createPredictionCard(race, predictions))
        }
        current.forEach { race ->
            containerCurrent.addView(createPredictionCard(race, predictions))
        }
        upcoming.forEach { race ->
            val card = createPredictionCard(race, predictions)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 12.dpToPx()
            card.layoutParams = params
            containerUpcoming.addView(card)
        }
    }

    private fun createPredictionCard(race: RaceEntity, predictions: List<PredictionEntity>): View {
        val racePredictions = predictions.filter { it.raceId == race.id }
        val card = LayoutInflater.from(this)
            .inflate(R.layout.item_prediction_race, null, false)

        card.findViewById<TextView>(R.id.tv_round).text = race.round
        card.findViewById<TextView>(R.id.tv_country).text = race.country
        card.findViewById<TextView>(R.id.tv_city).text = race.city

        bindPosition(card, race, racePredictions, 1, R.id.tv_p1, R.id.btn_clear_p1)
        bindPosition(card, race, racePredictions, 2, R.id.tv_p2, R.id.btn_clear_p2)
        bindPosition(card, race, racePredictions, 3, R.id.tv_p3, R.id.btn_clear_p3)

        return card
    }

    private fun bindPosition(
        card: View,
        race: RaceEntity,
        predictions: List<PredictionEntity>,
        position: Int,
        tvId: Int,
        clearBtnId: Int
    ) {
        val tv = card.findViewById<TextView>(tvId)
        val clearBtn = card.findViewById<TextView>(clearBtnId)
        val existing = predictions.find { it.position == position }

        if (existing != null) {
            tv.text = existing.driverName
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            tv.typeface = resources.getFont(R.font.formula1_regular)
            clearBtn.visibility = View.VISIBLE
            clearBtn.setOnClickListener {
                deletePrediction(existing)
            }
        } else {
            tv.text = "tap to pick"
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_disabled))
            tv.typeface = resources.getFont(R.font.formula1_regular)
            clearBtn.visibility = View.GONE
        }

        tv.setOnClickListener {
            val selectedDrivers = predictions.map { it.driverName }
            val sheet = DriverPickerBottomSheet.newInstance(selectedDrivers) { driverName ->
                savePrediction(race.id, position, driverName, existing)
            }
            sheet.show(supportFragmentManager, "driver_picker")
        }
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

    private fun savePrediction(
        raceId: String,
        position: Int,
        driverName: String,
        existing: PredictionEntity?
    ) {
        thread {
            val db = AppDatabase.getInstance(this)
            if (existing != null) {
                db.predictionDao().update(existing.copy(driverName = driverName))
            } else {
                db.predictionDao().insert(
                    PredictionEntity(raceId = raceId, position = position, driverName = driverName)
                )
            }
            loadPredictions()
        }
    }

    private fun deletePrediction(prediction: PredictionEntity) {
        thread {
            val db = AppDatabase.getInstance(this)
            db.predictionDao().delete(prediction)
            loadPredictions()
        }
    }

    private fun Int.dpToPx(): Int =
        (this * resources.displayMetrics.density).toInt()
}