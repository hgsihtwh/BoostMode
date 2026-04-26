package com.example.boostmode

import org.json.JSONObject
import java.net.URL

object F1ApiService {

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
