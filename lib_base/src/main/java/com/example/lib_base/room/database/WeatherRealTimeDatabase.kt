package com.example.lib_base.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lib_base.room.conver.WeatherRealTimeConverter
import com.example.lib_base.room.dao.WeatherRealTimeDao
import com.example.lib_base.room.entity.WeatherRealTimeEntity

/**
 * @CreateDate: 2022/3/15 3:29 下午
 * @Author: 青柠
 * @Description:
 */

@Database(version = 1, exportSchema = false, entities = [WeatherRealTimeEntity::class])
@TypeConverters(WeatherRealTimeConverter::class)
abstract class WeatherRealTimeDatabase : RoomDatabase() {
    val weatherRealTimeDao: WeatherRealTimeDao by lazy { createWeatherRealTimeDao() }
    abstract fun createWeatherRealTimeDao(): WeatherRealTimeDao
}