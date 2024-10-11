package com.example.lib_main.ui.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.dialog.BaseCenterDialog
import com.example.lib_base.dialog.BaseFullDialog
import com.example.lib_base.ext.init
import com.example.lib_main.R
import com.example.lib_main.databinding.DialogSearchCityBinding
import com.example.lib_main.ui.adapter.SearchCityAdapter
import com.example.lib_main.utils.ReadJsonArrayUtils

/**
 * @CreateDate: 2024/10/12 03:23
 * @Author: 青柠
 * @Description:
 */
class SearchCityDialog(context: Context, val confirm: (String) -> Unit) :
    BaseCenterDialog<DialogSearchCityBinding>(context, R.layout.dialog_search_city) {

    private val searchCityAdapter: SearchCityAdapter by lazy { SearchCityAdapter() }

    //所有城市数据
    private val cityDataList = ReadJsonArrayUtils().fromAssets(context, "cityData.json")

    override fun onCreate() {
        super.onCreate()


        mDatabind?.run {

            rvSearch.init(GridLayoutManager(context, 1), searchCityAdapter, false)
            searchCityAdapter.run {
                setOnItemClickListener { _, view, position ->
                    val data = getItem(position) ?: return@setOnItemClickListener
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
                    if (cityDataList.isNullOrEmpty()) return

                    val newList = cityDataList.filter {
                        it.formattedAddress.contains(etSearch.text.toString().trim())
                    }

                    searchCityAdapter.submitList(newList)
                }

                override fun afterTextChanged(s: Editable?) {
                    // 在文本变化之后执行的操作
                }
            })
        }
    }
}