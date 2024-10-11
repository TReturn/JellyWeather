package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.databinding.ItemFutureWeatherBinding
import com.example.lib_main.model.WeatherDayList
import com.example.lib_main.model.getSky

/**
 * @CreateDate: 2024/10/11 15:49
 * @Author: 青柠
 * @Description:
 */
class FutureWeatherAdapter :
    BaseQuickAdapter<WeatherDayList, FutureWeatherAdapter.VH>() {

    private var highMinValue = 100
    private var highMaxValue = 0

    private var lowMinValue = 100
    private var lowMaxValue = 0

    class VH(
        parent: ViewGroup,
        val binding: ItemFutureWeatherBinding = ItemFutureWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: WeatherDayList?) {
        holder.binding.run {
            if (item == null) return
            data = item
            executePendingBindings()

            tvWeather.text = getSky(item.daySkycon).info
            tvWeatherNight.text = getSky(item.nightSkycon).info
            GlideUtils.loadImage(context, getSky(item.daySkycon).icon, ivWeather)
            GlideUtils.loadImage(context, getSky(item.nightSkycon).icon, ivWeatherNight)

            //温度折线图
            for (i in items.indices) {
                //赋值数组中的最大值
                if (highMaxValue < items[i].max) {
                    highMaxValue = items[i].max.toInt()
                }
                if (highMinValue > items[i].max) {
                    highMinValue = items[i].max.toInt()
                }

                //赋值数组中的最小值
                if (lowMaxValue < items[i].min) {
                    lowMaxValue = items[i].min.toInt()
                }
                if (lowMinValue > items[i].min) {
                    lowMinValue = items[i].min.toInt()
                }
            }

            if (position == 0) {
                temperatureViewHigh.setDrawLeftLine(false)
                temperatureViewLow.setDrawLeftLine(false)
            } else {
                temperatureViewHigh.setDrawLeftLine(true)
                temperatureViewHigh.setLastValue(items[position - 1].max.toInt())
                temperatureViewLow.setDrawLeftLine(true)
                temperatureViewLow.setLastValue(items[position - 1].min.toInt())
            }

            //如果是最后一个
            if (position == items.size - 1) {
                temperatureViewHigh.setDrawRightLine(false)
                temperatureViewLow.setDrawRightLine(false)
            } else {
                temperatureViewHigh.setDrawRightLine(true)
                temperatureViewHigh.setNextValue(items[position + 1].max.toInt())
                temperatureViewLow.setDrawRightLine(true)
                temperatureViewLow.setNextValue(items[position + 1].min.toInt())
            }

            temperatureViewHigh.setCurrentValue(items[position].max.toInt())
            temperatureViewHigh.setMinValue(highMinValue)
            temperatureViewHigh.setMaxValue(highMaxValue)

            temperatureViewLow.setCurrentValue(items[position].min.toInt())
            temperatureViewLow.setMinValue(lowMinValue)
            temperatureViewLow.setMaxValue(lowMaxValue)
        }
    }


}