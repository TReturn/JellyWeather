package com.example.lib_base.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lib_base.room.conver.WeatherDayConverter
import com.example.lib_base.room.dao.WeatherDayDao
import com.example.lib_base.room.entity.WeatherDayEntity

/**
 * @CreateDate: 2022/3/15 3:29 下午
 * @Author: 青柠
 * @Description:
 */

@Database(version = 1, exportSchema = false, entities = [WeatherDayEntity::class])
@TypeConverters(WeatherDayConverter::class)
abstract class WeatherDayDatabase : RoomDatabase() {
    val weatherDayDao: WeatherDayDao by lazy { createWeatherDayDao() }
    abstract fun createWeatherDayDao(): WeatherDayDao
}