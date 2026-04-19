package com.example.boostmode.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boostmode.database.entity.DriverEntity

@Dao
interface DriverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(drivers: List<DriverEntity>)

    @Query("SELECT * FROM drivers")
    fun getAll(): List<DriverEntity>

    @Query("SELECT * FROM drivers WHERE team = :team")
    fun getByTeam(team: String): List<DriverEntity>
}