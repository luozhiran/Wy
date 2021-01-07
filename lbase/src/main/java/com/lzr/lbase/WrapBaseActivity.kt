package com.lzr.lbase

import android.Manifest
import android.content.pm.PackageManager
import com.plugin.itg_util.Utils
import com.yk.base.BaseActivity

open abstract class WrapBaseActivity : BaseActivity() {


    fun requestPermission(vararg permission: String) {
        val permission = Utils.checkPermission(this, permission)
        if (permission?.size!! > 0) {
            Utils.requestPermission(permission, this, 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionFail(PermissionExplanation.transPermissionChinese(permissions[index]))
                    return
                }
            }
            requestPermissionSuccess()
        }
    }

    open fun requestPermissionSuccess() {

    }

    open fun requestPermissionFail(rejectPermission: String) {

    }

}