package com.lzr.lbase

import android.Manifest


class PermissionExplanation {

    companion object {
        fun transPermissionChinese(permission: String): String {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE == permission || Manifest.permission.READ_EXTERNAL_STORAGE == permission) {
                return "允许程序写入外部存储,如SD卡上写文件"
            } else {
                return "你拒绝了权限申请"
            }
        }
    }
}