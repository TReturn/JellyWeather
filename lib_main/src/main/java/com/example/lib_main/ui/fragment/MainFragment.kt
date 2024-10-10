package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.model.getSky
import com.example.lib_main.viewmodel.MainViewModel

/**
 * @CreateDate: 2023/8/24 17:10
 * @Author: 青柠
 * @Description: 项目首页
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)
    }

    override fun initData() {
        mViewModel.getWeatherRealTime()
        mViewModel.getWeatherDay()
    }

    @SuppressLint("DefaultLocale")
    override fun createObserver() {
        super.createObserver()

        mViewModel.weatherRealTimeData.observe(viewLifecycleOwner) {
            //体感温度
            mViewModel.weatherTemp.value = "${String.format("%.1f", it.temperature)}°"
            //湿度
            mViewModel.weatherHumidity.value = "${it.humidity * 100}%"
            //天气状态
            mViewModel.weatherStatus.value = getSky(it.skycon).info
            //天气图标
            mViewModel.weatherIcon.value = getSky(it.skycon).icon
        }

        mViewModel.weatherDayData.observe(viewLifecycleOwner) {
            //最高温、最低温
            val maxTemp = it.temperature[0].max.toInt()
            val minTemp = it.temperature[0].min.toInt()
            mViewModel.weatherTopTemp.value = "$maxTemp°/$minTemp°"
        }

    }

    inner class ProxyClick {

    }

}