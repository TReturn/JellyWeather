package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocation
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_base.room.manager.CityManager
import com.example.lib_base.utils.log.LogUtils
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

    val weatherCity = MutableLiveData<String>()

    val isShowMagicIndicator = MutableLiveData<Boolean>()

    /**
     * 获取城市列表
     */
    fun getCityList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list: MutableList<CityEntity> =
                    CityManager.cityDB.cityDao.getList() as MutableList<CityEntity>
                if (list.isEmpty()) {
                    //列表为空，增加一条
                    list.add(CityEntity("香港特别行政区", "22.277468", "114.171203", true))
                }
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
                    val cityName = if (amapLocation.district.isNullOrEmpty()) {
                        amapLocation.city
                    } else if (amapLocation.city.isNullOrEmpty()) {
                        amapLocation.province
                    } else if (amapLocation.province.isNullOrEmpty()) {
                        "未知"
                    } else {
                        amapLocation.district
                    }

                    //更新数据库
                    updateLocationDataBase(
                        cityName,
                        amapLocation.latitude.toString(),
                        amapLocation.longitude.toString()
                    )

//                    LogUtils.d(
//                        "\n纬度:${amapLocation.latitude}"
//                                + "\n经度:${amapLocation.longitude}"
//                                + "\n省信息:${amapLocation.province}"
//                                + "\n城市信息:${amapLocation.city}"
//                                + "\n城区信息:${amapLocation.district}"
//                                + "\n街道信息:${amapLocation.street}"
//
//                    )
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
                    weatherCity.postValue(cityName)
                } else {
                    //更新数据库
                    for (city in cityList) {
                        if (city.isMyLocation) {
                            if (city.cityName != cityName) {
                                //定位变动，更新天气
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
        isShowMagicIndicator.value = true
    }

}