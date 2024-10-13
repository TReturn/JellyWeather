package com.example.lib_main.utils

import android.content.Context
import com.example.lib_base.model.CityDataModel
import org.json.JSONObject

/**
 * @CreateDate: 2024/10/12 01:26
 * @Author: 青柠
 * @Description:
 */
class ReadJsonArrayUtils {
    fun fromAssets(context: Context, fileName: String): List<CityDataModel>? {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonObject = JSONObject(jsonString)
            // 获取 "data" 字段
            val jsonArray = jsonObject.getJSONArray("data")

            val cityDataList = mutableListOf<CityDataModel>()
            for (i in 0 until jsonArray.length()) {
                val cityJsonObject = jsonArray.getJSONObject(i)
                val cityName = cityJsonObject.getString("formatted_address")
                val lng = cityJsonObject.getString("lng")
                val lat = cityJsonObject.getString("lat")
                cityDataList.add(
                    CityDataModel(
                        cityName,
                        lng,
                        lat
                    )
                )
            }

            return cityDataList
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}