package com.example.lib_main.ui.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lib_base.BaseApplication
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.bindViewPager2
import com.example.lib_base.ext.init
import com.example.lib_base.magic.ScaleCircleNavigator
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.model.getSky
import com.example.lib_main.viewmodel.MainViewModel
import com.hjq.toast.Toaster
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import net.lucode.hackware.magicindicator.abs.IPagerNavigator

/**
 * @CreateDate: 2023/8/24 17:10
 * @Author: 青柠
 * @Description: 项目首页
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    //是否已创建好ViewPager
    private var isInitViewPager = false

    private var circleNavigator: IPagerNavigator? = null

    private var cityNameList: MutableList<String> = arrayListOf()

    //上一个城市天气背景
    private var lastWeatherBg = 0

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

    }

    override fun onResume() {
        super.onResume()
        QMUIStatusBarHelper.setStatusBarDarkMode(mActivity)
    }

    override fun initData() {
        initNavigator()
        mViewModel.getCityList()
        mViewModel.getLocation()
    }


    @SuppressLint("DefaultLocale")
    override fun createObserver() {
        super.createObserver()

        appViewModel.resumeWeatherStatus.observeInFragment(this) {
            if (lastWeatherBg == 0) {
                lastWeatherBg = getSky(it).bg
            }
            changeBackgroundSmoothly(mDatabind.clRoot, getSky(it).bg)
        }

        mViewModel.cityList.observe(viewLifecycleOwner) {
            cityNameList.clear()
            val fragmentList: ArrayList<Fragment> = arrayListOf()
            for (city in it) {
                cityNameList.add(city.cityName)
                fragmentList.add(WeatherDetailFragment.newInstance(city.lat, city.lng))
            }

            initViewPager2(fragmentList)

            if (cityNameList.isNotEmpty()) {
                mViewModel.weatherCity.value = cityNameList[0]
            }

        }

    }

    /**
     * 初始化viewpager2
     * @param fragmentList ArrayList<Fragment>
     */
    private fun initViewPager2(fragmentList: ArrayList<Fragment>) {
        //初始化viewpager2
        mDatabind.vpWeather.init(this, fragmentList)
        //初始化magicIndicator
        mDatabind.magicIndicator.bindViewPager2(mDatabind.vpWeather, circleNavigator) {
            if (it < cityNameList.size) {
                mViewModel.weatherCity.value = cityNameList[it]
            }

        }

        if (cityNameList.size >= 2) {
            mDatabind.vpWeather.offscreenPageLimit = 1
        }

        isInitViewPager = true

        //更新小圆点选中
        (circleNavigator as ScaleCircleNavigator).setCircleCount(fragmentList.size)
        circleNavigator?.notifyDataSetChanged()
    }

    /**
     * 初始化ViewPager圆点
     */
    private fun initNavigator() {
        circleNavigator = ScaleCircleNavigator(BaseApplication.context)
        (circleNavigator as ScaleCircleNavigator).setNormalCircleColor(Color.parseColor("#4DFFFFFF"))
        (circleNavigator as ScaleCircleNavigator).setSelectedCircleColor(Color.WHITE)
    }

    /**
     * 切换城市背景平滑过渡
     * @param view View
     * @param newBg Int
     */
    private fun changeBackgroundSmoothly(view: View, newBg: Int) {
        val layers = arrayOfNulls<Drawable>(2)
        layers[0] = mActivity.getDrawable(lastWeatherBg)
        layers[1] = mActivity.getDrawable(newBg)

        val transitionDrawable = TransitionDrawable(layers)
        view.background = transitionDrawable
        transitionDrawable.startTransition(500)
        lastWeatherBg = newBg
    }


    inner class ProxyClick {
        fun toLocation() {
            nav().navigateAction(R.id.action_main_to_location)

        }
    }

}