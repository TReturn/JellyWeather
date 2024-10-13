package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import androidx.lifecycle.viewModelScope
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.model.WeatherDayModel
import com.example.lib_base.model.WeatherRealTimeModel
import com.example.lib_base.room.conver.WeatherDayConverter
import com.example.lib_base.room.conver.WeatherRealTimeConverter
import com.example.lib_base.room.entity.WeatherDayEntity
import com.example.lib_base.room.entity.WeatherRealTimeEntity
import com.example.lib_base.room.manager.WeatherDayManager
import com.example.lib_base.room.manager.WeatherRealTimeManager
import com.example.lib_base.utils.log.LogUtils
import com.example.lib_main.model.getSky
import com.example.lib_main.utils.CacheTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    //温度数据
    val weatherTemp = MutableLiveData<String>()
    val weatherTopTemp = MutableLiveData<String>()
    val weatherHumidity = MutableLiveData<String>()
    val weatherStatus = MutableLiveData<String>()
    val weatherIcon = MutableLiveData<Int>()

    //日出日落
    val weatherSunrise = MutableLiveData<String>()
    val weatherSunset = MutableLiveData<String>()

    //风向
    val weatherWindDirection = MutableLiveData<String>()

    //风速
    val weatherWindSpeed = MutableLiveData<String>()

    /**
     * 获取实时天气
     */
    fun getWeatherRealTime(cityName: String, lat: String, lng: String) {
        //先查询数据库，无数据、数据过期再查询网络
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data =
                    WeatherRealTimeManager.weatherRealTimeDB.weatherRealTimeDao.getDataByCityName(
                        cityName
                    )

                if (data == null) {
                    requestWeatherRealTime(cityName, lat, lng)
                    log("实时天气-$cityName:无数据，请求网络")
                } else {
                    val realTimeData = WeatherRealTimeConverter().to(data.realTimeData)
                    weatherRealTimeData.postValue(realTimeData)
                    log("实时天气-$cityName，有数据，使用数据库")
                    //30分钟以内缓存有效
                    if (!CacheTimeUtils.isWithin30Minutes(data.time, System.currentTimeMillis())) {
                        requestWeatherRealTime(cityName, lat, lng)
                        log("实时天气-$cityName，数据库过期，请求网络")
                    }
                }

            } catch (exception: Exception) {
                requestWeatherRealTime(cityName, lat, lng)
                exception.printStackTrace()
            }
        }

    }

    /**
     * 网络请求获取实时天气
     * @param lat String
     * @param lng String
     */
    private fun requestWeatherRealTime(cityName: String, lat: String, lng: String) {
        scopeNetLife {
            val data = Get<WeatherRealTimeModel>(ApiUrls.getRealTimeWeather(lat, lng)) {
            }.await()

            weatherRealTimeData.value = data.result.realtime
            saveWeatherRealTime(cityName, data.result.realtime)
        }
    }

    /**
     * 保存实时天气
     * @param cityName String
     * @param data Realtime
     */
    private fun saveWeatherRealTime(cityName: String, data: WeatherRealTimeModel.Result.Realtime) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val realtimeData =
                    WeatherRealTimeManager.weatherRealTimeDB.weatherRealTimeDao.getDataByCityName(
                        cityName
                    )

                val weatherRealTimeEntity =
                    WeatherRealTimeEntity(cityName, WeatherRealTimeConverter().from(data))
                if (realtimeData == null) {
                    WeatherRealTimeManager.weatherRealTimeDB.weatherRealTimeDao.save(
                        weatherRealTimeEntity
                    )
                } else {
                    WeatherRealTimeManager.weatherRealTimeDB.weatherRealTimeDao.update(
                        weatherRealTimeEntity
                    )
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    /**
     * 获取未来几天天气
     * @param lat String
     * @param lng String
     */
    fun getWeatherDay(cityName: String, lat: String, lng: String) {

        //先查询数据库，无数据、数据过期再查询网络
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data =
                    WeatherDayManager.weatherDayDB.weatherDayDao.getDataByCityName(
                        cityName
                    )

                if (data == null) {
                    requestWeatherDay(cityName, lat, lng)
                    log("未来天气-$cityName:无数据，请求网络")
                } else {
                    val dayData = WeatherDayConverter().to(data.dayData)
                    weatherDayData.postValue(dayData)
                    log("未来天气-$cityName:有数据，使用数据库")
                    //缓存超过两小时，请求网络
                    if (!CacheTimeUtils.isWithin2Hours(data.time, System.currentTimeMillis())) {
                        requestWeatherDay(cityName, lat, lng)
                        log("未来天气-$cityName:数据库过期，请求网络")
                    }
                }

            } catch (exception: Exception) {
                requestWeatherDay(cityName, lat, lng)
                exception.printStackTrace()
            }
        }

    }

    /**
     * 网络请求获取未来几天天气
     * @param cityName String
     * @param lat String
     * @param lng String
     */
    private fun requestWeatherDay(cityName: String, lat: String, lng: String) {
        scopeNetLife {
            val data = Get<WeatherDayModel>(ApiUrls.getDayWeather(lat, lng, "7")) {
            }.await()

            weatherDayData.value = data.result.daily
            saveWeatherDay(cityName, data.result.daily)
        }
    }

    /**
     * 保存未来几天天气
     * @param cityName String
     * @param data Realtime
     */
    private fun saveWeatherDay(cityName: String, data: WeatherDayModel.Result.Daily) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dayData =
                    WeatherDayManager.weatherDayDB.weatherDayDao.getDataByCityName(
                        cityName
                    )

                val weatherDayEntity =
                    WeatherDayEntity(cityName, WeatherDayConverter().from(data))
                if (dayData == null) {
                    WeatherDayManager.weatherDayDB.weatherDayDao.save(
                        weatherDayEntity
                    )
                } else {
                    WeatherDayManager.weatherDayDB.weatherDayDao.update(
                        weatherDayEntity
                    )
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    private fun log(msg: String) {
        LogUtils.d(msg)
    }

    init {
        weatherTemp.value = "--°"
        weatherTopTemp.value = "--°/--°"
        weatherHumidity.value = "--%"
        weatherStatus.value = ""
        weatherIcon.value = getSky("CLEAR_DAY").icon
    }
}