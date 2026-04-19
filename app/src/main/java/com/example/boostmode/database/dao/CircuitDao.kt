package com.example.boostmode.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boostmode.database.entity.CircuitEntity

@Dao
interface CircuitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(circuits: List<CircuitEntity>)

    @Query("SELECT * FROM circuits WHERE raceId = :raceId")
    fun getByRaceId(raceId: String): CircuitEntity?

    @Query("SELECT * FROM circuits")
    fun getAll(): List<CircuitEntity>
}