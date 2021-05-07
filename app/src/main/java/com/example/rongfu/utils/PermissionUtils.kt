package com.example.rongfu.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

object PermissionUtils {

    private val PERMISSION_REQUEST_CODE = 100

    fun checkSdPermission(context: Context): Boolean {

        val disablePermissionList = validatePermissions(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        return disablePermissionList.isEmpty()
    }


    fun checkSdAndRequestPermission(activity: Activity): Boolean {

        val disablePermissionList = validatePermissions(
            activity.applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        return if (checkSdPermission(activity)) {
            true
        } else {
            //开始请求权限
            ActivityCompat.requestPermissions(
                activity,
                disablePermissionList.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
            false
        }
    }

    /**
     * 验证权限是否已经获得
     * @param permissions 需要的权限数组
     * @return 未获得的权限列表
     */
    fun validatePermissions(context: Context, vararg permissions: String): List<String> {
        val disablePermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                disablePermissionList.add(permission)
            }
        }
        return disablePermissionList
    }


}