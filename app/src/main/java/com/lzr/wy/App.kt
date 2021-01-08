package com.lzr.wy

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.okhttp_lib.okhttp.interceptors.LoggerInterceptor
import okhttp3.Interceptor
import okhttp3.Response

class App : Application() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(USER_XML, Context.MODE_PRIVATE)
        ItgOk.Builder()
            .globalUrl("http://localhost:8081/")
            .openHttpLog(true)
            .logLevel(LoggerInterceptor.HL.BODY) //
            .addInterceptors(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val r = chain.request().newBuilder()
                        .addHeader("token", sharedPreferences.getString("token", "")).build()
                    return chain.proceed(r)
                }

            })
            .write("${Environment.getExternalStorageDirectory().absolutePath}/netLog.txt")//不要开启，开启后写文件会加长网络响应时间
            .build(this)
    }
}