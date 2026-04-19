package com.example.boostmode.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boostmode.database.entity.RaceEntity

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(races: List<RaceEntity>)

    @Query("SELECT * FROM races")
    fun getAll(): List<RaceEntity>

    @Query("SELECT * FROM races WHERE id = :id")
    fun getById(id: String): RaceEntity?

    @Query("SELECT * FROM races WHERE status = :status")
    fun getByStatus(status: String): List<RaceEntity>
}