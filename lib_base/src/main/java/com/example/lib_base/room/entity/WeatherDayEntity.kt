package com.example.lib_base.room.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description:
 */

@Entity(tableName = "weather_day")
class WeatherDayEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var time: Long = 0

    //城市名称
    var cityName: String = ""

    //天气数据，使用String存储
    var dayData: String = ""

    @Ignore
    constructor(
        cityName: String,
        dayData: String
    ) : this() {
        time = System.currentTimeMillis()
        this.cityName = cityName
        this.dayData = dayData

    }
}