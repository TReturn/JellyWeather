package com.example.lib_base.room.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description:
 */

@Entity(tableName = "note")
class CityEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var time: Long = 0

    //编码
    var code: String = ""

    //城市名称
    var cityName: String = ""

    //经度
    var lat: String = ""

    //纬度
    var lng: String = ""

    //是否为当前定位城市
    var isMyLocation: Boolean = false

    @Ignore
    constructor(
        cityName: String,
        lat: String = "",
        lng: String="",
        isMyLocation: Boolean = false
    ) : this() {
        time = System.currentTimeMillis()
        this.cityName = cityName
        this.lat = lat
        this.lng = lng
        this.isMyLocation = isMyLocation
    }
}