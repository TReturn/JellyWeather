package com.example.lib_base.room.manager

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lib_base.BaseApplication
import com.example.lib_base.room.database.WeatherDayDatabase

/**
 * @CreateDate: 2022/3/15 3:30 下午
 * @Author: 青柠
 * @Description:
 */
object WeatherDayManager {
    private const val WEATHER_DAY_DB_NAME = "WeatherDay.db"

    private val MIGRATIONS = arrayOf(Migration1)
    private var application: Application = BaseApplication.context

    val weatherDayDB: WeatherDayDatabase by lazy {
        Room.databaseBuilder(
            application.applicationContext,
            WeatherDayDatabase::class.java,
            WEATHER_DAY_DB_NAME
        )
            .addCallback(CreatedCallBack)
            .addMigrations(*MIGRATIONS)
            .build()
    }

    fun saveApplication(application: Application) {
        WeatherDayManager.application = application
    }

    private object CreatedCallBack : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            //在新装app时会调用，调用时机为数据库build()之后，数据库升级时不调用此函数
            MIGRATIONS.map {
                Migration1.migrate(db)
            }
        }
    }

    private object Migration1 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 数据库的升级语句
            //database.execSQL("alert table note add column newTitle varchar NOT NULL")
        }
    }
}