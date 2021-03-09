package com.lzr.wy.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lzr.lbase.UINCallback
import com.lzr.lbase.NetMainThreadCallback
import com.lzr.wy.IP
import com.lzr.wy.USER_XML
import com.lzr.wy.bean.LoginInfoData
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD

class LoginViewModel : ViewModel() {

    fun login(name: String, pwd: String, hub: KProgressHUD, UINCallback: UINCallback<Int>) {
        hub?.show()
        ItgOk
            .instance()
            .url("${IP}/login-register-service/login")
            .method(ItgOk.POST)
            .addParams("name", name)
            .addParams("pwd", pwd)
            .go(object : NetMainThreadCallback<LoginInfoData>(hub, LoginInfoData::class.java) {
                override fun onFailure(code: Int, error: String?) {

                }

                override fun onResponse(t: LoginInfoData?, msg: String?) {
                    val sharedPreferences = ItgOk.instance().application.getSharedPreferences(
                        USER_XML,
                        Context.MODE_PRIVATE
                    ).edit()
                    sharedPreferences.putString(
                        "token",
                        t?.token
                    ).apply()
                    UINCallback.callback(200)
                }


            })

    }

    fun register(
        phone: String,
        pwd: String,
        email: String,
        code: String,
        hub: KProgressHUD?,
        UINCallback: UINCallback<Int>
    ) {
        hub?.show()
        ItgOk
            .instance()
            .url("${IP}/register")
            .method(ItgOk.POST)
            .addParams("phone", phone)
            .addParams("pwd", pwd)
            .addParams("email", email)
            .addParams("vcode", code)
            .go(object : NetMainThreadCallback<String>(hub,String::class.java) {
                override fun onFailure(code: Int, error: String?) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(t: String?, msg: String?) {
                    TODO("Not yet implemented")
                }


            })
    }

}

