package com.example.lib_base.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lib_base.room.entity.WeatherRealTimeEntity


/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description: 数据库增删改查语句
 */

@Dao
interface WeatherRealTimeDao {
    @Insert
    fun save(vararg logs: WeatherRealTimeEntity): List<Long>

    @Delete
    fun delete(vararg logs: WeatherRealTimeEntity): Int

    @Update
    fun update(vararg logs: WeatherRealTimeEntity)

    @Query("select time from weather_real_time order by time asc limit 1")
    fun getFirstLogTime(): Long

    @Query("select time from weather_real_time order by time desc limit 1")
    fun getLastLogTime(): Long

    @Query("select * from weather_real_time where time>=:startTime and time <=:endTime")
    fun getLogByFilter(startTime: Long, endTime: Long): List<WeatherRealTimeEntity>

    fun getList(startTime: Long = 0, endTime: Long = 0): List<WeatherRealTimeEntity> {
        val start = if (startTime == 0L) {
            getFirstLogTime()
        } else {
            startTime
        }
        val end = if (endTime == 0L) {
            getLastLogTime()
        } else {
            endTime
        }
        return getLogByFilter(start, end)
    }

    @Query("select * from weather_real_time where id = :id")
    fun getDataByID(id: Int): WeatherRealTimeEntity

    @Query("select * from weather_real_time where cityName = :name")
    fun getDataByCityName(name: String): WeatherRealTimeEntity?

    @Query("DELETE FROM weather_real_time")
    fun deleteAll(): Int

}