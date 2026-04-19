package com.example.boostmode.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.boostmode.database.entity.PredictionEntity

@Dao
interface PredictionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(prediction: PredictionEntity)

    @Update
    fun update(prediction: PredictionEntity)

    @Delete
    fun delete(prediction: PredictionEntity)

    @Query("SELECT * FROM predictions")
    fun getAll(): List<PredictionEntity>

    @Query("SELECT * FROM predictions WHERE raceId = :raceId")
    fun getByRaceId(raceId: String): List<PredictionEntity>

    @Query("SELECT * FROM predictions WHERE raceId = :raceId AND position = :position")
    fun getByRaceAndPosition(raceId: String, position: Int): PredictionEntity?

    @Query("DELETE FROM predictions WHERE raceId = :raceId AND position = :position")
    fun deleteByRaceAndPosition(raceId: String, position: Int)
}