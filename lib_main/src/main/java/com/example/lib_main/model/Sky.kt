package com.example.lib_main.model

import com.example.lib_main.R


class Sky(val info: String, val icon: Int, val bg: Int)

private val sky = mapOf(
    "CLEAR_DAY" to Sky(
        "晴",
        R.drawable.ic_sunny_light,
        R.drawable.shape_sun_bg
    ),
    "CLEAR_NIGHT" to Sky(
        "晴",
        R.drawable.ic_sunny_night,
        R.drawable.shape_sun_bg
    ),
    "PARTLY_CLOUDY_DAY" to Sky(
        "多云",
        R.drawable.ic_cloudy_light,
        R.drawable.shape_cloudy_bg
    ),
    "PARTLY_CLOUDY_NIGHT" to Sky(
        "多云",
        R.drawable.ic_cloudy_night,
        R.drawable.shape_cloudy_bg
    ),
    "CLOUDY" to Sky(
        "阴",
        R.drawable.ic_overcast_sky,
        R.drawable.shape_rain_bg
    ),
    "LIGHT_HAZE" to Sky(
        "轻度雾霾",
        R.drawable.ic_haze,
        R.drawable.shape_fog_bg
    ),
    "MODERATE_HAZE" to Sky(
        "中度雾霾",
        R.drawable.ic_haze,
        R.drawable.shape_fog_bg
    ),
    "HEAVY_HAZE" to Sky(
        "重度雾霾",
        R.drawable.ic_haze,
        R.drawable.shape_fog_bg
    ),
    "LIGHT_RAIN" to Sky(
        "小雨",
        R.drawable.ic_small_rain,
        R.drawable.shape_rain_bg
    ),
    "MODERATE_RAIN" to Sky(
        "中雨",
        R.drawable.ic_moderate_rain,
        R.drawable.shape_rain_bg
    ),
    "HEAVY_RAIN" to Sky(
        "大雨",
        R.drawable.ic_heavy_rain,
        R.drawable.shape_rain_bg
    ),
    "STORM_RAIN" to Sky(
        "暴雨",
        R.drawable.ic_rainstorm,
        R.drawable.shape_rain_bg
    ),
    "FOG" to Sky(
        "雾",
        R.drawable.ic_the_fog,
        R.drawable.shape_fog_bg
    ),
    "LIGHT_SNOW" to Sky(
        "小雪",
        R.drawable.ic_light_snow,
        R.drawable.shape_snow_bg,
    ),
    "MODERATE_SNOW" to Sky(
        "中雪",
        R.drawable.ic_moderate_snow,
        R.drawable.shape_snow_bg
    ),
    "HEAVY_SNOW" to Sky(
        "大雪",
        R.drawable.ic_heavy_snow,
        R.drawable.shape_snow_bg
    ),
    "STORM_SNOW" to Sky(
        "暴雪",
        R.drawable.ic_blizzard,
        R.drawable.shape_snow_bg
    ),
    "DUST" to Sky(
        "浮尘",
        R.drawable.ic_sand_blowing,
        R.drawable.shape_fog_bg
    ),
    "SAND" to Sky(
        "沙尘",
        R.drawable.ic_sand_blowing,
        R.drawable.shape_fog_bg
    ),
    "WIND" to Sky(
        "大风",
        R.drawable.ic_sand_blowing,
        R.drawable.shape_fog_bg
    ),
)

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}