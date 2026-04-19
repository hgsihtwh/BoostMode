package com.example.boostmode

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RaceScheduleActivity : AppCompatActivity() {

    data class Session(
        val name: String,
        val date: String,
        val time: String,
        val isRace: Boolean = false
    )

    data class ScheduleData(
        val round: String,
        val city: String,
        val gpName: String,
        val sessions: List<Session>
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_race_schedule)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val scheduleId = intent.getStringExtra("schedule_id") ?: return
        val data = getScheduleData(scheduleId)
        bindData(data)
    }

    private fun bindData(data: ScheduleData) {
        findViewById<TextView>(R.id.tv_round).text = data.round
        findViewById<TextView>(R.id.tv_city).text = data.city
        findViewById<TextView>(R.id.tv_gp_name).text = data.gpName

        val container = findViewById<LinearLayout>(R.id.container_sessions)
        container.removeAllViews()

        data.sessions.forEach { session ->
            val row = layoutInflater.inflate(R.layout.item_session, container, false)

            val color = if (session.isRace)
                ContextCompat.getColor(this, R.color.accent_red)
            else
                ContextCompat.getColor(this, R.color.text_primary)

            row.findViewById<TextView>(R.id.tv_session_name).apply {
                text = session.name
                setTextColor(color)
            }
            row.findViewById<TextView>(R.id.tv_session_date).apply {
                text = session.date
                setTextColor(color)
            }
            row.findViewById<TextView>(R.id.tv_session_time).apply {
                text = session.time
                setTextColor(color)
            }

            container.addView(row)
        }
    }

    private fun getScheduleData(scheduleId: String): ScheduleData {
        return when (scheduleId) {
            "australia" -> ScheduleData(
                round = "01",
                city = "Melbourne",
                gpName = "Australian GP",
                sessions = listOf(
                    Session("Practice 1", "06 mar", "11:30-12:30"),
                    Session("Practice 2", "06 mar", "15:00-16:00"),
                    Session("Practice 3", "07 mar", "11:30-12:30"),
                    Session("Qualifying", "07 mar", "15:00-16:00"),
                    Session("Race", "08 mar", "14:00", isRace = true)
                )
            )
            "china" -> ScheduleData(
                round = "02",
                city = "Shanghai",
                gpName = "China GP",
                sessions = listOf(
                    Session("Practice 1", "13 mar", "11:30-12:30"),
                    Session("Sprint Qualifying", "13 mar", "15:30-16:14"),
                    Session("Sprint", "14 mar", "11:00-12:00"),
                    Session("Qualifying", "14 mar", "15:00-16:00"),
                    Session("Race", "15 mar", "15:00", isRace = true)
                )
            )
            "japan" -> ScheduleData(
                round = "03",
                city = "Suzuka",
                gpName = "Japanese GP",
                sessions = listOf(
                    Session("Practice 1", "27 mar", "09:30-10:30"),
                    Session("Practice 2", "27 mar", "13:00-14:00"),
                    Session("Practice 3", "28 mar", "09:30-10:30"),
                    Session("Qualifying", "29 mar", "13:00-14:00"),
                    Session("Race", "29 mar", "12:00", isRace = true)
                )
            )
            "bahrain" -> ScheduleData(
                round = "04",
                city = "Sakhir",
                gpName = "Bahrain GP",
                sessions = listOf(
                    Session("Practice 1", "10 apr", "13:30-14:30"),
                    Session("Practice 2", "10 apr", "17:00-18:00"),
                    Session("Practice 3", "11 apr", "13:30-14:30"),
                    Session("Qualifying", "11 apr", "17:00-18:00"),
                    Session("Race", "12 apr", "15:00", isRace = true)
                )
            )
            "saudi" -> ScheduleData(
                round = "05",
                city = "Jeddah",
                gpName = "Saudi Arabian GP",
                sessions = listOf(
                    Session("Practice 1", "17 apr", "17:30-18:30"),
                    Session("Practice 2", "17 apr", "21:00-22:00"),
                    Session("Practice 3", "18 apr", "17:30-18:30"),
                    Session("Qualifying", "18 apr", "21:00-22:00"),
                    Session("Race", "19 apr", "20:00", isRace = true)
                )
            )
            else -> ScheduleData(
                round = "06",
                city = "Miami",
                gpName = "Miami GP",
                sessions = listOf(
                    Session("Practice 1", "01 may", "12:30-13:30"),
                    Session("Sprint Qualifying", "01 may", "16:30-17:14"),
                    Session("Sprint", "02 may", "12:00-13:00"),
                    Session("Qualifying", "02 may", "16:00-17:00"),
                    Session("Race", "03 may", "16:00", isRace = true)
                )
            )
        }
    }
}