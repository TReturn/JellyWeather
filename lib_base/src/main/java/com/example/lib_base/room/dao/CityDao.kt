package com.example.lib_base.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lib_base.room.entity.CityEntity


/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description: 数据库增删改查语句
 */

@Dao
interface CityDao {
    @Insert
    fun save(vararg logs: CityEntity): List<Long>

    @Delete
    fun delete(vararg logs: CityEntity): Int

    @Update
    fun update(vararg logs: CityEntity)

    @Query("select time from city order by time asc limit 1")
    fun getFirstLogTime(): Long

    @Query("select time from city order by time desc limit 1")
    fun getLastLogTime(): Long

    @Query("select * from city where time>=:startTime and time <=:endTime")
    fun getLogByFilter(startTime: Long, endTime: Long): List<CityEntity>

    fun getList(startTime: Long = 0, endTime: Long = 0): List<CityEntity> {
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

    @Query("select * from city where id = :id")
    fun getDataByID(id: Int): CityEntity

    @Query("DELETE FROM city")
    fun deleteAll(): Int

}