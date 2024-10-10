package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.net.WeatherSerializationConverter
import com.example.lib_main.model.WeatherDayModel
import com.example.lib_main.model.WeatherRealTimeModel
import com.example.lib_main.model.getSky
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/8/24 17:11
 * @Author: 青柠
 * @Description:
 */
class MainViewModel : BaseViewModel() {

    //实时天气数据
    val weatherRealTimeData = MutableLiveData<WeatherRealTimeModel.Result.Realtime>()

    //未来几天天气数据
    val weatherDayData = MutableLiveData<WeatherDayModel.Result.Daily>()

    val weatherCity = MutableLiveData<String>()
    val weatherTemp = MutableLiveData<String>()
    val weatherTopTemp = MutableLiveData<String>()
    val weatherHumidity = MutableLiveData<String>()
    val weatherStatus = MutableLiveData<String>()
    val weatherIcon = MutableLiveData<Int>()

    /**
     * 获取实时天气
     */
    fun getWeatherRealTime(lng: String = "121.6544", lat: String = "25.1552") {
        scopeNetLife {
            val data = Get<WeatherRealTimeModel>(ApiUrls.getRealTimeWeather(lng, lat)) {
            }.await()

            weatherRealTimeData.value = data.result.realtime
        }
    }

    /**
     * 获取未来几天天气
     * @param lng String
     * @param lat String
     * @param day String
     */
    fun getWeatherDay(lng: String = "121.6544", lat: String = "25.1552", day: String = "7") {
        scopeNetLife {
            val data = Get<WeatherDayModel>(ApiUrls.getDayWeather(lng, lat, day)) {
            }.await()

            weatherDayData.value = data.result.daily
        }
    }

    init {
        weatherCity.value = "天河区"
        weatherTemp.value = "--°"
        weatherTopTemp.value = "--°/--°"
        weatherHumidity.value = "--%"
        weatherStatus.value = ""
        weatherIcon.value = getSky("CLEAR_DAY").icon
    }
}