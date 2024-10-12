package com.example.lib_main.model


/**
 * @CreateDate: 2024/10/12 01:20
 * @Author: 青柠
 * @Description:
 */

data class CityDataModel(
    var adcode: String = "",
    var formattedAddress: String = "",
    var lat: String = "",
    var lng: String = "",
    var isAdd: Boolean = false,
)