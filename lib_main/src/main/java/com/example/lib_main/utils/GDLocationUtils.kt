package com.example.lib_main.utils

import coil.util.Logger
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.lib_base.BaseApplication
import com.example.lib_base.utils.log.LogUtils


/**
 * @CreateDate: 2024/10/11 20:25
 * @Author: 青柠
 * @Description: 高德定位
 */
object GDLocationUtils {


    //声明AMapLocationClient类对象
    private var mLocationClient: AMapLocationClient? = null

    private var successLister: OnGDLocationSuccessListener? = null

    interface OnGDLocationSuccessListener {
        fun onSuccess(amapLocation: AMapLocation)
    }

    //设置成功回调监听
    fun setGDLocationSuccessListener(listener: OnGDLocationSuccessListener?) {
        successLister = listener
    }

    //设置定位回调监听
    private val mLocationListener = AMapLocationListener { amapLocation ->
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //可在其中解析amapLocation获取相应内容。
                successLister?.onSuccess(amapLocation)
                stop()
                destroy()
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtils.d(
                    "location Error, ErrCode:"
                            + amapLocation.errorCode + ", errInfo:"
                            + amapLocation.errorInfo
                );
            }
        }
    }

    fun initLocation(): GDLocationUtils {
        //初始化定位
        AMapLocationClient.updatePrivacyShow(BaseApplication.context, true, true)
        AMapLocationClient.updatePrivacyAgree(BaseApplication.context, true)

        mLocationClient = AMapLocationClient(BaseApplication.context)
        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener)


        //声明AMapLocationClientOption对象
        var mLocationOption: AMapLocationClientOption? = null

        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()


        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving)


        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true)
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true)

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true)


        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient?.startLocation()

        return this

    }

    private fun stop() {
        //停止定位后，本地定位服务并不会被销毁
        mLocationClient?.stopLocation()
    }

    private fun destroy() {
        //销毁定位客户端，同时销毁本地定位服务。
        mLocationClient?.onDestroy()
        mLocationClient = null
    }

}