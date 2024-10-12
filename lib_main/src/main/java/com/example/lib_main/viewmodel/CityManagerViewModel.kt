package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_base.room.manager.CityManager
import com.example.lib_main.model.CityDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2024/10/11 20:50
 * @Author: 青柠
 * @Description:
 */
class CityManagerViewModel : BaseViewModel() {

    //已添加的城市列表
    val cityList = MutableLiveData<List<CityEntity>>()

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
     * 更新城市列表
     * @param data CityDataModel
     */
    fun updateCityList(data: CityDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cityEntity = CityEntity(data.formattedAddress, data.lat, data.lng)
                CityManager.cityDB.cityDao.save(cityEntity)

                //刷新数据
                getCityList()
            } catch (exception: Exception) {
                exception.printStackTrace()

            }
        }
    }

    /**
     * 删除城市
     * @param id Int
     */
    fun deleteCity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = CityManager.cityDB.cityDao.getDataByID(id)
                CityManager.cityDB.cityDao.delete(list)

                //刷新数据
                getCityList()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}