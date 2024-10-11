package com.example.lib_main.model

import kotlinx.serialization.SerialName

/**
 * @CreateDate: 2024/10/11 18:01
 * @Author: 青柠
 * @Description:
 */
data class WeatherDayList(
    //日期
    var day: String = "",
    //星期
    var week: String = "",
    //白天天气状况
    var daySkycon: String = "",
    //夜间天气状况
    var nightSkycon: String = "",
    //最高温度
    var max: Double = 0.0,
    //最低温度
    var min: Double = 0.0
)