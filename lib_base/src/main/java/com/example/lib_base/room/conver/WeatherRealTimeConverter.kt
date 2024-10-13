package com.example.lib_base.room.conver

import androidx.room.TypeConverter
import com.example.lib_base.model.WeatherRealTimeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @CreateDate: 2024/10/13 16:49
 * @Author: 青柠
 * @Description: 转换器，用于把数据结构转存为Room支持的类型
 */
class WeatherRealTimeConverter {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun from(value: WeatherRealTimeModel.Result.Realtime): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun to(value: String): WeatherRealTimeModel.Result.Realtime {
        return Gson().fromJson(value, WeatherRealTimeModel.Result.Realtime::class.java)
    }
}