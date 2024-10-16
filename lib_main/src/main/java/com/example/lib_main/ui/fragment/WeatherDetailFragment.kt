package com.example.lib_main.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_main.databinding.FragmentWeatherDetailBinding
import com.example.lib_main.model.WeatherDayList
import com.example.lib_main.model.getSky
import com.example.lib_main.ui.adapter.FutureWeatherAdapter
import com.example.lib_main.ui.widget.FutureWeekUtils
import com.example.lib_main.viewmodel.WeatherDetailViewModel
import com.hjq.toast.Toaster

/**
 * @CreateDate: 2024/10/12 18:45
 * @Author: 青柠
 * @Description:
 */
class WeatherDetailFragment : BaseFragment<WeatherDetailViewModel, FragmentWeatherDetailBinding>() {

    //未来天气
    private val futureWeatherAdapter: FutureWeatherAdapter by lazy { FutureWeatherAdapter() }

    //当前天气
    private var skycon = ""

    //是否为定位天气
    private var isLocation = false

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        initAdapter()

        arguments?.run {
            val cityName = getString(CITY_NAME) ?: ""
            val lat = getString(LAT) ?: ""
            val lng = getString(LNG) ?: ""
            isLocation = getBoolean(IS_LOCATION)

            mViewModel.getWeatherRealTime(cityName, lat, lng)
            mViewModel.getWeatherDay(cityName, lat, lng)
        }
    }

    override fun onResume() {
        super.onResume()

        if (!TextUtils.isEmpty(skycon)) {
            appViewModel.resumeWeatherStatus.value = skycon
        }
    }

    private fun initAdapter() {
        //天气趋势折线图
        mDatabind.rvFutureWeather.init(
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false),
            futureWeatherAdapter,
            false
        )
    }

    override fun createObserver() {
        super.createObserver()

        //刷新定位天气
        appViewModel.refreshLocationWeather.observeInFragment(this) {
            if (isLocation) {
                mViewModel.getWeatherRealTime(it.cityName, it.lat, it.lng)
                mViewModel.getWeatherDay(it.cityName, it.lat, it.lng)
            }
        }

        //实时数据
        mViewModel.weatherRealTimeData.observe(viewLifecycleOwner) {
            //体感温度
            mViewModel.weatherTemp.value = "${String.format("%.1f", it.temperature)}°"
            //湿度
            mViewModel.weatherHumidity.value = "${(it.humidity * 100).toInt()}%"
            //天气状态
            mViewModel.weatherStatus.value = getSky(it.skycon).info
            //天气图标
            mViewModel.weatherIcon.value = getSky(it.skycon).icon

            skycon = it.skycon
            if (isResumed) {
                appViewModel.resumeWeatherStatus.value = skycon
            }
        }

        //未来天气
        mViewModel.weatherDayData.observe(viewLifecycleOwner) {
            //最高温、最低温
            val maxTemp = it.temperature[0].max.toInt()
            val minTemp = it.temperature[0].min.toInt()
            mViewModel.weatherTopTemp.value = "$maxTemp°/$minTemp°"

            //日出日落
            mViewModel.weatherSunrise.value = it.astro[0].sunrise.time
            mViewModel.weatherSunset.value = it.astro[0].sunset.time

            //风向风速
            mViewModel.weatherWindDirection.value = "${it.wind[0].max.direction}"
            mViewModel.weatherWindSpeed.value = "${it.wind[0].max.speed}"

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

            if (tempList.size > 2) {
                tempList[0].week = "今天"
                tempList[1].week = "明天"
            }
            futureWeatherAdapter.submitList(tempList)
        }
    }

    inner class ProxyClick {

    }

    companion object {
        private const val CITY_NAME = "CITY_NAME"
        private const val LAT = "LAT"
        private const val LNG = "LNG"
        private const val IS_LOCATION = "IS_LOCATION"
        fun newInstance(
            cityName: String,
            lat: String,
            lng: String,
            isLocation: Boolean
        ): WeatherDetailFragment {
            return WeatherDetailFragment().apply {
                this.arguments = Bundle().apply {
                    putString(CITY_NAME, cityName)
                    putString(LAT, lat)
                    putString(LNG, lng)
                    putBoolean(IS_LOCATION, isLocation)
                }
            }
        }
    }
}