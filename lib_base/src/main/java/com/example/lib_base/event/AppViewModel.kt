package com.example.lib_base.event

import com.example.lib_base.model.CityDataModel
import com.example.lib_base.room.entity.CityEntity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 * @CreateDate: 2022/6/14 11:35
 * @Author: 青柠
 * @Description: APP全局ViewModel,可在任何位置监听、回调，相当于EventBus。
 */
class AppViewModel : BaseViewModel() {

    //跳转到首页Tab，0第一个Tab，1第二个，以此类推
    val intentToMainTab = EventLiveData<Int>()

    //重启APP
    val isRestart = EventLiveData<Boolean>()

    //天气状态
    val resumeWeatherStatus = EventLiveData<String>()

    //刷新定位天气
    val refreshLocationWeather = EventLiveData<CityDataModel>()

}