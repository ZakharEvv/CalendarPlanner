package com.zszuev.taskmanager.data.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zszuev.calendarplanner.data.dbmodels.TaskDBModel
import javax.inject.Inject

@androidx.room.Database(entities = [TaskDBModel::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {

        private var INSTANSE: Database? = null
        private val LOCK = Any()
        private const val DB_NAME = "tasks.db"

        fun getInstance(application: Application) : Database {
            INSTANSE?.let{
                return it
            }
            synchronized(LOCK) {
                INSTANSE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    Database::class.java,
                    DB_NAME
                ).build()
                INSTANSE = db
                return db
            }

        }
    }
}