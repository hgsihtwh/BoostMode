package com.example.boostmode.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "circuits")
data class CircuitEntity(
    @PrimaryKey
    val raceId: String,
    val location: String,
    val opened: String,
    val firstF1Gp: String,
    val turns: String,
    val length: String,
    val laps: String,
    val about: String,
    val lapTimeRecord: String,
    val driver: String,
    val car: String,
    val year: String,
    val avgSpeed: String
)