package com.example.lib_main.model

import com.example.lib_main.R


class Sky(val info: String, val icon: Int)

private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴", R.drawable.ic_sunny_light),
    "CLEAR_NIGHT" to Sky("晴", R.drawable.ic_sunny_night),
    "PARTLY_CLOUDY_DAY" to Sky("多云", R.drawable.ic_cloudy_light),
    "PARTLY_CLOUDY_NIGHT" to Sky("多云", R.drawable.ic_cloudy_night),
    "CLOUDY" to Sky("阴", R.drawable.ic_overcast_sky),
    "LIGHT_HAZE" to Sky("轻度雾霾", R.drawable.ic_haze),
    "MODERATE_HAZE" to Sky("中度雾霾", R.drawable.ic_haze),
    "HEAVY_HAZE" to Sky("重度雾霾", R.drawable.ic_haze),
    "LIGHT_RAIN" to Sky("小雨", R.drawable.ic_small_rain),
    "MODERATE_RAIN" to Sky("中雨", R.drawable.ic_moderate_rain),
    "HEAVY_RAIN" to Sky("大雨", R.drawable.ic_heavy_rain),
    "STORM_RAIN" to Sky("暴雨", R.drawable.ic_rainstorm),
    "FOG" to Sky("雾", R.drawable.ic_the_fog),
    "LIGHT_SNOW" to Sky("小雪", R.drawable.ic_light_snow),
    "MODERATE_SNOW" to Sky("中雪", R.drawable.ic_moderate_snow),
    "HEAVY_SNOW" to Sky("大雪", R.drawable.ic_heavy_snow),
    "STORM_SNOW" to Sky("暴雪", R.drawable.ic_blizzard),
    "DUST" to Sky("浮尘", R.drawable.ic_sand_blowing),
    "SAND" to Sky("沙尘", R.drawable.ic_sand_blowing),
    "WIND" to Sky("大风", R.drawable.ic_sand_blowing),
)

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}