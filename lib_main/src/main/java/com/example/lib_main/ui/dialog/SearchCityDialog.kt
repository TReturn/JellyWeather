package com.example.lib_main.ui.dialog

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.dialog.BaseBottomDialog
import com.example.lib_base.ext.init
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_base.room.manager.CityManager
import com.example.lib_main.R
import com.example.lib_main.databinding.DialogSearchCityBinding
import com.example.lib_main.model.CityDataModel
import com.example.lib_main.ui.adapter.SearchCityAdapter
import com.example.lib_main.utils.ReadJsonArrayUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @CreateDate: 2024/10/12 03:23
 * @Author: 青柠
 * @Description:
 */
class SearchCityDialog(context: Context, private val confirm: (CityDataModel) -> Unit) :
    BaseBottomDialog<DialogSearchCityBinding>(context, R.layout.dialog_search_city) {

    //已添加的城市列表
    private val cityList: MutableList<CityEntity> = arrayListOf()

    private val searchCityAdapter: SearchCityAdapter by lazy { SearchCityAdapter() }

    //所有城市数据
    private val cityDataList = ReadJsonArrayUtils().fromAssets(context, "cityData.json")

    override fun onCreate() {
        super.onCreate()

        getCityList()

        mDatabind?.run {

            rvSearch.init(GridLayoutManager(context, 1), searchCityAdapter, false)
            searchCityAdapter.run {
                setOnItemClickListener { _, view, position ->
                    val data = getItem(position) ?: return@setOnItemClickListener
                    if (data.isAdd) return@setOnItemClickListener

                    confirm.invoke(data)
                    dismiss()
                }
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // 在文本变化之前执行的操作
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (TextUtils.isEmpty(etSearch.text.toString())) {
                        //清空列表
                        searchCityAdapter.submitList(listOf(CityDataModel()))
                        return
                    }

                    //搜索列表
                    if (cityDataList.isNullOrEmpty()) return
                    val newList = cityDataList.filter {
                        it.formattedAddress.contains(etSearch.text.toString().trim())
                    }

                    //已添加过的城市
                    val cityNameMap = HashMap<String, Boolean>()
                    // 将 cityList 中的城市名称添加到 HashMap 中
                    for (city in cityList) {
                        cityNameMap[city.cityName] = true
                    }
                    // 遍历 newList，查找匹配项
                    for (i in newList.indices) {
                        newList[i].isAdd = cityNameMap.containsKey(newList[i].formattedAddress)
                    }

                    searchCityAdapter.submitList(newList)
                }

                override fun afterTextChanged(s: Editable?) {
                    // 在文本变化之后执行的操作
                }
            })
        }
    }

    /**
     * 获取城市列表
     */
    private fun getCityList() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                cityList.clear()
                cityList.addAll(CityManager.cityDB.cityDao.getList())
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}