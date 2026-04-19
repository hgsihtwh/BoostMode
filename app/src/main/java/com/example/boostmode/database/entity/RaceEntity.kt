package com.example.boostmode.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "races")
data class RaceEntity(
    @PrimaryKey
    val id: String,
    val country: String,
    val city: String,
    val round: String,
    val dates: String,
    val status: String
)