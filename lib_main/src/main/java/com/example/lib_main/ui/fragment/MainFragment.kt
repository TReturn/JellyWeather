package com.example.lib_main.ui.fragment

import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.viewmodel.MainViewModel

/**
 * @CreateDate: 2023/8/24 17:10
 * @Author: 青柠
 * @Description: 项目首页
 */
class MainFragment: BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
    }

    override fun createObserver() {
        super.createObserver()

    }

}