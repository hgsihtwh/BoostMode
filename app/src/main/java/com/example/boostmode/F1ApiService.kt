package com.example.boostmode

import org.json.JSONObject
import java.net.URL

object F1ApiService {

    data class ConstructorStanding(
        val name: String,
        val position: String,
        val points: String
    )

    fun getConstructorStandings(): List<ConstructorStanding> {
        return try {
            val url = "https://api.jolpi.ca/ergast/f1/2026/constructorStandings.json"
            val response = URL(url).readText()
            val standingsLists = JSONObject(response)
                .getJSONObject("MRData")
                .getJSONObject("StandingsTable")
                .getJSONArray("StandingsLists")

            if (standingsLists.length() == 0) return emptyList()

            val standings = standingsLists.getJSONObject(0).getJSONArray("ConstructorStandings")
            (0 until standings.length()).map { i ->
                val s = standings.getJSONObject(i)
                ConstructorStanding(
                    name = s.getJSONObject("Constructor").getString("name"),
                    position = s.getString("position"),
                    points = s.getString("points")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getTopThree(round: Int): List<String> {
        return try {
            val url = "https://api.jolpi.ca/ergast/f1/2026/$round/results.json"
            val response = URL(url).readText()
            val races = JSONObject(response)
                .getJSONObject("MRData")
                .getJSONObject("RaceTable")
                .getJSONArray("Races")

            if (races.length() == 0) return emptyList()

            val results = races.getJSONObject(0).getJSONArray("Results")
            (0 until minOf(3, results.length())).map { i ->
                val driver = results.getJSONObject(i).getJSONObject("Driver")
                val given = driver.getString("givenName").split(" ").last()
                "$given ${driver.getString("familyName")}"
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
