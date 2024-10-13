package com.example.lib_base.permission

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

/**
 * @CreateDate: 2023/9/14 16:43
 * @Author: 青柠
 * @Description: 统一处理权限逻辑
 */
object PermissionUtils {


    /**
     * 定位权限
     */
    fun location(context: FragmentActivity, onCallBack: (Boolean) -> Unit) {
        PermissionX.init(context)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList, "申请定位权限",
                    "同意", "取消"
                )
            }

            .request { allGranted, _, _ ->
                if (allGranted) {
                    onCallBack.invoke(true)
                } else {
                    onCallBack.invoke(false)
                }
            }
    }


}