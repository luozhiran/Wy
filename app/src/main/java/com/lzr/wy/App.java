package com.lzr.wy;

import android.app.Application;

import com.plugin.okhttp_lib.okhttp.ItgOk;
import com.plugin.okhttp_lib.okhttp.interceptors.LoggerInterceptor;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new ItgOk.Builder()
                .openHttpLog(true)
                .logLevel(LoggerInterceptor.HL.BODY)
                // .write(Environment.getExternalStorageDirectory() + "/order.txt")//不要开启，开启后写文件会加长网络响应时间
                .build(this);
    }
}
