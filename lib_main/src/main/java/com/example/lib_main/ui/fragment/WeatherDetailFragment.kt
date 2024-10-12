package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_main.databinding.FragmentWeatherDetailBinding
import com.example.lib_main.model.WeatherDayList
import com.example.lib_main.model.getSky
import com.example.lib_main.ui.adapter.FutureWeatherAdapter
import com.example.lib_main.ui.widget.FutureWeekUtils
import com.example.lib_main.viewmodel.WeatherDetailViewModel

/**
 * @CreateDate: 2024/10/12 18:45
 * @Author: 青柠
 * @Description:
 */
class WeatherDetailFragment : BaseFragment<WeatherDetailViewModel, FragmentWeatherDetailBinding>() {

    //未来天气
    private val futureWeatherAdapter: FutureWeatherAdapter by lazy { FutureWeatherAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        initAdapter()

        arguments?.run {
            val lat = getString(LAT) ?: ""
            val lng = getString(LNG) ?: ""
            mViewModel.getWeatherRealTime(lat, lng)
            mViewModel.getWeatherDay(lat, lng)
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

    }

    companion object {
        private const val LAT = "LAT"
        private const val LNG = "LNG"
        fun newInstance(lat: String, lng: String): WeatherDetailFragment {
            return WeatherDetailFragment().apply {
                this.arguments = Bundle().apply {
                    putString(LAT, lat)
                    putString(LNG, lng)
                }
            }
        }
    }
}