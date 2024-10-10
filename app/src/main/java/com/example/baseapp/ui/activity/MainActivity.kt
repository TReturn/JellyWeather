package com.example.baseapp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.viewmodel.MainViewModel
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.hjq.toast.Toaster


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel

        //沉浸状态栏
        QMUIStatusBarHelper.translucent(this)

    }

    override fun createObserver() {
        super.createObserver()
        appViewModel.isRestart.observeInActivity(this) {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }

}