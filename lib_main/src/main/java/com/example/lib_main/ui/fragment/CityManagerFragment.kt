package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.dialog.DialogManager
import com.example.lib_base.ext.init
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentCityManagerBinding
import com.example.lib_main.ui.adapter.CityManagerAdapter
import com.example.lib_main.ui.dialog.SearchCityDialog
import com.example.lib_main.viewmodel.CityManagerViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2024/10/11 20:50
 * @Author: 青柠
 * @Description:
 */
class CityManagerFragment : BaseFragment<CityManagerViewModel, FragmentCityManagerBinding>() {

    //城市管理
    private val cityManagerAdapter: CityManagerAdapter by lazy { CityManagerAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)
        QMUIStatusBarHelper.setStatusBarLightMode(mActivity)

        mDatabind.include.titleBar.title = "选择城市"
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })

        initAdapter()
    }

    override fun initData() {
        mViewModel.getCityList()
    }

    private fun initAdapter() {
        mDatabind.rvCity.init(GridLayoutManager(context, 1), cityManagerAdapter, false)
        cityManagerAdapter.run {
            setOnItemClickListener { _, view, position ->
                val data = getItem(position) ?: return@setOnItemClickListener
                if (position == 0) {
                    //增加城市
                    DialogManager.get(mActivity).asCustom(SearchCityDialog(mActivity) {
                        mViewModel.updateCityList(it)
                    }).show()
                }
            }

            addOnItemChildClickListener(R.id.ivDelete) { _, _, position ->
                val data = getItem(position) ?: return@addOnItemChildClickListener
                if (data.isLocation) return@addOnItemChildClickListener
                //删除城市
                mViewModel.deleteCity(data.id)
            }
        }
    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.cityList.observe(viewLifecycleOwner) {
            val newList: MutableList<CityEntity> = mutableListOf()
            newList.add(CityEntity(cityName = "增加城市", isLocation = true))
            newList.addAll(it)

            cityManagerAdapter.submitList(newList)
        }
    }

    inner class ProxyClick {

    }
}