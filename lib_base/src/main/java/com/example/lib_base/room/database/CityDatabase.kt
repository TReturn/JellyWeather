package com.example.lib_base.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lib_base.room.dao.CityDao
import com.example.lib_base.room.entity.CityEntity

/**
 * @CreateDate: 2022/3/15 3:29 下午
 * @Author: 青柠
 * @Description:
 */

@Database(version = 1, exportSchema = false, entities = [CityEntity::class])
abstract class CityDatabase : RoomDatabase() {
    val cityDao: CityDao by lazy { createCityDao() }
    abstract fun createCityDao(): CityDao
}