package com.example.lib_base.constant

/**
 * @CreateDate: 2020/12/30
 * @Author: 青柠
 * @Description: 存放网络请求URL
 */
object ApiUrls {

    const val BASE_WAN_URL = "https://api.caiyunapp.com/v2.6/"

    /**
     * 彩云天气相关API
     */

    //彩云天气API
    const val CAIYUN_WEATHER_REALTIME =
        "https://api.caiyunapp.com/v2.6/${SdkKeys.CAIYUN_WEATHER_KEY}"

    //实况天气
    fun getRealTimeWeather(lat: String, lng: String): String {
        return "${CAIYUN_WEATHER_REALTIME}/${lng},${lat}/realtime"
    }

    //以天为单位的天气
    fun getDayWeather(lat: String, lng: String, day: String = "7"): String {
        return "${CAIYUN_WEATHER_REALTIME}/${lng},${lat}/daily?dailysteps=$day"

    }

}