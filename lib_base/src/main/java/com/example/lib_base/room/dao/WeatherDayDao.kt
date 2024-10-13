package com.example.lib_base.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lib_base.room.entity.WeatherDayEntity


/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description: 数据库增删改查语句
 */

@Dao
interface WeatherDayDao {
    @Insert
    fun save(vararg logs: WeatherDayEntity): List<Long>

    @Delete
    fun delete(vararg logs: WeatherDayEntity): Int

    @Update
    fun update(vararg logs: WeatherDayEntity)

    @Query("select time from weather_day order by time asc limit 1")
    fun getFirstLogTime(): Long

    @Query("select time from weather_day order by time desc limit 1")
    fun getLastLogTime(): Long

    @Query("select * from weather_day where time>=:startTime and time <=:endTime")
    fun getLogByFilter(startTime: Long, endTime: Long): List<WeatherDayEntity>

    fun getList(startTime: Long = 0, endTime: Long = 0): List<WeatherDayEntity> {
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

    @Query("select * from weather_day where id = :id")
    fun getDataByID(id: Int): WeatherDayEntity

    @Query("select * from weather_day where cityName = :name")
    fun getDataByCityName(name: String): WeatherDayEntity?

    @Query("DELETE FROM weather_day")
    fun deleteAll(): Int

}