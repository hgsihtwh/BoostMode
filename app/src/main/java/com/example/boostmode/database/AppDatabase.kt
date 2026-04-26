package com.example.boostmode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boostmode.database.dao.CircuitDao
import com.example.boostmode.database.dao.DriverDao
import com.example.boostmode.database.dao.PredictionDao
import com.example.boostmode.database.dao.RaceDao
import com.example.boostmode.database.entity.CircuitEntity
import com.example.boostmode.database.entity.DriverEntity
import com.example.boostmode.database.entity.PredictionEntity
import com.example.boostmode.database.entity.RaceEntity

@Database(
    entities = [
        RaceEntity::class,
        CircuitEntity::class,
        DriverEntity::class,
        PredictionEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun raceDao(): RaceDao
    abstract fun circuitDao(): CircuitDao
    abstract fun driverDao(): DriverDao
    abstract fun predictionDao(): PredictionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "boostmode.db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}