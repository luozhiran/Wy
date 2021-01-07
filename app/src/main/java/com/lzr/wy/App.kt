package com.lzr.wy

import android.app.Application
import android.os.Environment
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.okhttp_lib.okhttp.interceptors.LoggerInterceptor

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ItgOk.Builder()
            .globalUrl("http://localhost:8081/")
            .openHttpLog(true)
            .logLevel(LoggerInterceptor.HL.BODY) //
            .write("${Environment.getExternalStorageDirectory().absolutePath}/netLog.txt")//不要开启，开启后写文件会加长网络响应时间
            .build(this)
    }
}