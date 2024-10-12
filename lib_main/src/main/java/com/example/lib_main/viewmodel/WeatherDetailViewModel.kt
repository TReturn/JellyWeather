package com.example.lib_main.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_main.model.WeatherDayModel
import com.example.lib_main.model.WeatherRealTimeModel
import com.example.lib_main.model.getSky
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2024/10/12 18:45
 * @Author: 青柠
 * @Description:
 */
class WeatherDetailViewModel : BaseViewModel() {

    //实时天气数据
    val weatherRealTimeData = MutableLiveData<WeatherRealTimeModel.Result.Realtime>()

    //未来几天天气数据
    val weatherDayData = MutableLiveData<WeatherDayModel.Result.Daily>()

    val weatherTemp = MutableLiveData<String>()
    val weatherTopTemp = MutableLiveData<String>()
    val weatherHumidity = MutableLiveData<String>()
    val weatherStatus = MutableLiveData<String>()
    val weatherIcon = MutableLiveData<Int>()

    /**
     * 获取实时天气
     */
    fun getWeatherRealTime(lat: String, lng: String) {
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) return
        scopeNetLife {
            val data = Get<WeatherRealTimeModel>(ApiUrls.getRealTimeWeather(lat, lng)) {
            }.await()

            weatherRealTimeData.value = data.result.realtime
        }
    }

    /**
     * 获取未来几天天气
     * @param lat String
     * @param lng String
     * @param day String
     */
    fun getWeatherDay(lat: String, lng: String, day: String = "7") {
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) return
        scopeNetLife {
            val data = Get<WeatherDayModel>(ApiUrls.getDayWeather(lat, lng, day)) {
            }.await()

            weatherDayData.value = data.result.daily
        }
    }

    init {
        weatherTemp.value = "--°"
        weatherTopTemp.value = "--°/--°"
        weatherHumidity.value = "--%"
        weatherStatus.value = ""
        weatherIcon.value = getSky("CLEAR_DAY").icon
    }
}