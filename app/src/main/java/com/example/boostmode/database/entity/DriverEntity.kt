package com.example.boostmode.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drivers")
data class DriverEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val team: String,
    val number: String
)