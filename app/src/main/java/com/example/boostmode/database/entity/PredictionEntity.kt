package com.example.boostmode.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "predictions")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val raceId: String,
    val position: Int,
    val driverName: String
)