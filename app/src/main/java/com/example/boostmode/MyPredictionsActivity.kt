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

            val apiResults = mutableMapOf<String, List<String>>()
            races.filter { it.status == "previous" }.forEach { race ->
                apiResults[race.id] = F1ApiService.getTopThree(race.round.toInt())
            }

            runOnUiThread {
                bindPredictions(races, predictions, apiResults)
            }
        }
    }

    private fun bindPredictions(
        races: List<RaceEntity>,
        predictions: List<PredictionEntity>,
        apiResults: Map<String, List<String>>
    ) {
        val containerPrevious = findViewById<LinearLayout>(R.id.container_previous)
        val containerCurrent = findViewById<LinearLayout>(R.id.container_current)
        val containerUpcoming = findViewById<LinearLayout>(R.id.container_upcoming)

        containerPrevious.removeAllViews()
        containerCurrent.removeAllViews()
        containerUpcoming.removeAllViews()

        races.filter { it.status == "previous" }.forEach { race ->
            containerPrevious.addView(createCard(race, predictions, apiResults[race.id]))
        }
        races.filter { it.status == "current" }.forEach { race ->
            containerCurrent.addView(createCard(race, predictions, null))
        }
        races.filter { it.status == "upcoming" }.forEach { race ->
            containerUpcoming.addView(createCard(race, predictions, null))
        }
    }

    private fun createCard(
        race: RaceEntity,
        predictions: List<PredictionEntity>,
        topThree: List<String>?
    ): View {
        val racePredictions = predictions.filter { it.raceId == race.id }
        val card = LayoutInflater.from(this).inflate(R.layout.item_prediction_race, null, false)

        card.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = 12.dpToPx() }

        card.findViewById<TextView>(R.id.tv_round).text = race.round
        card.findViewById<TextView>(R.id.tv_country).text = race.country
        card.findViewById<TextView>(R.id.tv_city).text = race.city

        bindPosition(card, race, racePredictions, 1, R.id.tv_p1, R.id.btn_clear_p1)
        bindPosition(card, race, racePredictions, 2, R.id.tv_p2, R.id.btn_clear_p2)
        bindPosition(card, race, racePredictions, 3, R.id.tv_p3, R.id.btn_clear_p3)

        bindResult(card, R.id.tv_r1, topThree?.getOrNull(0))
        bindResult(card, R.id.tv_r2, topThree?.getOrNull(1))
        bindResult(card, R.id.tv_r3, topThree?.getOrNull(2))

        return card
    }

    private fun bindResult(card: View, tvId: Int, name: String?) {
        card.findViewById<TextView>(tvId).apply {
            if (name != null) {
                text = name
                setTextColor(ContextCompat.getColor(this@MyPredictionsActivity, R.color.text_primary))
            } else {
                text = "..."
                setTextColor(ContextCompat.getColor(this@MyPredictionsActivity, R.color.text_disabled))
            }
        }
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
            clearBtn.setOnClickListener { deletePrediction(existing) }
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
            AppDatabase.getInstance(this).predictionDao().delete(prediction)
            loadPredictions()
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}
