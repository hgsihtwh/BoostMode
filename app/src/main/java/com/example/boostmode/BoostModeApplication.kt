package com.example.boostmode

import android.app.Application
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.DatabaseSeeder
import kotlin.concurrent.thread

class BoostModeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        thread {
            seedDatabase()
        }
    }

    private fun seedDatabase() {
        val db = AppDatabase.getInstance(this)

        val raceDao = db.raceDao()
        val driverDao = db.driverDao()
        val circuitDao = db.circuitDao()

        if (raceDao.getAll().isEmpty()) {
            raceDao.insertAll(DatabaseSeeder.getRaces())
        }

        if (driverDao.getAll().isEmpty()) {
            driverDao.insertAll(DatabaseSeeder.getDrivers())
        }

        if (circuitDao.getAll().isEmpty()) {
            circuitDao.insertAll(DatabaseSeeder.getCircuits())
        }
    }
}