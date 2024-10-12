package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.model.WeatherDayList
import com.example.lib_main.model.getSky
import com.example.lib_main.ui.adapter.FutureWeatherAdapter
import com.example.lib_main.ui.widget.FutureWeekUtils
import com.example.lib_main.viewmodel.MainViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @CreateDate: 2023/8/24 17:10
 * @Author: 青柠
 * @Description: 项目首页
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    //当前是第几个Fragment
    private var indexFragment = 0

    //未来天气
    private val futureWeatherAdapter: FutureWeatherAdapter by lazy { FutureWeatherAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        QMUIStatusBarHelper.setStatusBarDarkMode(mActivity)
    }

    override fun initData() {
        mViewModel.getCityList()
        mViewModel.getLocation()
    }

    private fun initAdapter() {
        //天气趋势折线图
        mDatabind.rvFutureWeather.init(
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false),
            futureWeatherAdapter,
            false
        )
    }

    @SuppressLint("DefaultLocale")
    override fun createObserver() {
        super.createObserver()

        mViewModel.cityList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                mViewModel.getWeatherRealTime()
                mViewModel.getWeatherDay()
                return@observe
            }
            mViewModel.getWeatherRealTime(it[indexFragment].lat, it[indexFragment].lng)
            mViewModel.getWeatherDay(it[indexFragment].lat, it[indexFragment].lng)
            mViewModel.weatherCity.value = it[indexFragment].cityName
        }

        mViewModel.weatherRealTimeData.observe(viewLifecycleOwner) {
            //体感温度
            mViewModel.weatherTemp.value = "${String.format("%.1f", it.temperature)}°"
            //湿度
            mViewModel.weatherHumidity.value = "${(it.humidity * 100).toInt()}%"
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

            //天气趋势折线图
            val tempList: MutableList<WeatherDayList> = arrayListOf()
            for (i in it.temperature.indices) {
                tempList.add(
                    WeatherDayList(
                        day = FutureWeekUtils().getFutureWeekDates()[i],
                        week = FutureWeekUtils().getFutureWeekdays()[i],
                        max = it.temperature[i].max,
                        min = it.temperature[i].min,
                        daySkycon = it.skycon08h20h[i].value,
                        nightSkycon = it.skycon20h32h[i].value
                    )
                )

            }

            tempList[0].week = "今天"
            tempList[1].week = "明天"

            futureWeatherAdapter.submitList(tempList)
        }

    }


    inner class ProxyClick {
        fun toLocation() {
            nav().navigateAction(R.id.action_main_to_location)

        }
    }

}