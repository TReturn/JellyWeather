package com.example.lib_base.room.conver

import androidx.room.TypeConverter
import com.example.lib_base.model.WeatherDayModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @CreateDate: 2024/10/13 16:49
 * @Author: 青柠
 * @Description: 转换器，用于把数据结构转存为Room支持的类型
 */
class WeatherDayConverter {

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
    fun from(value: WeatherDayModel.Result.Daily): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun to(value: String): WeatherDayModel.Result.Daily {
        return Gson().fromJson(value, WeatherDayModel.Result.Daily::class.java)
    }
}