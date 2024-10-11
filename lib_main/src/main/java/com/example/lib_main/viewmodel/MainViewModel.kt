package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_base.room.manager.CityManager
import com.example.lib_base.utils.log.LogUtils
import com.example.lib_main.model.WeatherDayModel
import com.example.lib_main.model.WeatherRealTimeModel
import com.example.lib_main.model.getSky
import com.example.lib_main.utils.GDLocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/8/24 17:11
 * @Author: 青柠
 * @Description:
 */
class MainViewModel : BaseViewModel() {

    //已添加的城市列表
    val cityList = MutableLiveData<List<CityEntity>>()

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
    fun getWeatherRealTime(lat: String = "22.277468", lng: String = "114.171203") {
        scopeNetLife {
            val data = Get<WeatherRealTimeModel>(ApiUrls.getRealTimeWeather(lat, lng)) {
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
    fun getWeatherDay(lat: String = "22.277468", lng: String = "114.171203", day: String = "7") {
        scopeNetLife {
            val data = Get<WeatherDayModel>(ApiUrls.getDayWeather(lat, lng, day)) {
            }.await()

            weatherDayData.value = data.result.daily
        }
    }

    /**
     * 获取城市列表
     */
    fun getCityList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cityList.postValue(CityManager.cityDB.cityDao.getList())
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    /**
     * 获取定位
     */
    fun getLocation() {
        GDLocationUtils.initLocation()
            .setGDLocationSuccessListener(object : GDLocationUtils.OnGDLocationSuccessListener {
                override fun onSuccess(amapLocation: AMapLocation) {
                    //更新数据库
                    updateLocationDataBase(
                        amapLocation.street,
                        amapLocation.latitude.toString(),
                        amapLocation.longitude.toString()
                    )

                    LogUtils.d(
                        "\n纬度:${amapLocation.latitude}"
                                + "\n经度:${amapLocation.longitude}"
                                + "\n省信息:${amapLocation.province}"
                                + "\n城市信息:${amapLocation.city}"
                                + "\n城区信息:${amapLocation.district}"
                                + "\n街道信息:${amapLocation.street}"

                    )
                }

            })
    }

    /**
     * 更新定位城市到数据库
     * @param cityName String
     * @param lat String
     * @param lng String
     */
    private fun updateLocationDataBase(cityName: String, lat: String, lng: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cityList = CityManager.cityDB.cityDao.getList()
                if (cityList.isEmpty()) {
                    //首次增加定位城市
                    val cityEntity = CityEntity(cityName, lat, lng, true)
                    CityManager.cityDB.cityDao.save(cityEntity)

                    //首次定位，更新天气
                    getWeatherRealTime(lat, lng)
                    getWeatherDay(lat, lng)
                    weatherCity.postValue(cityName)
                } else {
                    //更新数据库
                    for (city in cityList) {
                        if (city.isMyLocation) {
                            if (city.cityName != cityName) {
                                //定位变动，更新天气
                                getWeatherRealTime(lat, lng)
                                getWeatherDay(lat, lng)
                                weatherCity.postValue(cityName)
                            }
                            city.cityName = cityName
                            city.lat = lat
                            city.lng = lng
                            CityManager.cityDB.cityDao.update(city)
                            break
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    init {
        weatherCity.value = "香港特别行政区"
        weatherTemp.value = "--°"
        weatherTopTemp.value = "--°/--°"
        weatherHumidity.value = "--%"
        weatherStatus.value = ""
        weatherIcon.value = getSky("CLEAR_DAY").icon
    }
}