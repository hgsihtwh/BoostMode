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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_predictions)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

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
        val container = findViewById<LinearLayout>(R.id.container_predictions)
        container.removeAllViews()

        races.forEach { race ->
            val racePredictions = predictions.filter { it.raceId == race.id }
            val card = LayoutInflater.from(this)
                .inflate(R.layout.item_prediction_race, container, false)

            card.findViewById<TextView>(R.id.tv_round).text = race.round
            card.findViewById<TextView>(R.id.tv_country).text = race.country
            card.findViewById<TextView>(R.id.tv_city).text = race.city

            bindPosition(card, race, racePredictions, 1, R.id.tv_p1, R.id.btn_clear_p1)
            bindPosition(card, race, racePredictions, 2, R.id.tv_p2, R.id.btn_clear_p2)
            bindPosition(card, race, racePredictions, 3, R.id.tv_p3, R.id.btn_clear_p3)

            container.addView(card)
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
            clearBtn.visibility = View.VISIBLE

            clearBtn.setOnClickListener {
                deletePrediction(existing)
            }
        } else {
            tv.text = "tap to pick"
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_disabled))
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

    private fun savePrediction(
        raceId: String,
        position: Int,
        driverName: String,
        existing: PredictionEntity?
    ) {
        thread {
            val db = AppDatabase.getInstance(this)
            if (existing != null) {
                db.predictionDao().update(
                    existing.copy(driverName = driverName)
                )
            } else {
                db.predictionDao().insert(
                    PredictionEntity(
                        raceId = raceId,
                        position = position,
                        driverName = driverName
                    )
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
}