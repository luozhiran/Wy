package com.lzr.wy.login

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.itg.lib_log.L
import com.lzr.lbase.ConvertObject
import com.lzr.lbase.NetMainThreadCallback
import com.lzr.wy.IP
import com.lzr.wy.USER_XML
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LoginViewModel : ViewModel() {

    fun login(name: String, pwd: String, hub: KProgressHUD, convertObject: ConvertObject<Int>) {
        hub?.show()
        ItgOk
            .instance()
            .url("$IP/login")
            .method(ItgOk.GET)
            .addParams("name", name)
            .addParams("pwd", pwd)
            .go(object : NetMainThreadCallback(hub) {
                override fun onResponse(response: Response?) {
                    val json = JSONObject(response?.body()?.string() ?: "")
                    val code = json.optInt("code")
                    if (code == 1) {
                        val sharedPreferences = ItgOk.instance().application.getSharedPreferences(
                            USER_XML,
                            Context.MODE_PRIVATE
                        ).edit()
                        sharedPreferences.putString(
                            "token",
                            json.optJSONObject("data")?.optString("token")
                        ).apply()
                        convertObject.callback(code);
                    }
                }

            })

    }

    fun register(
        phone: String,
        pwd: String,
        email: String,
        code: String,
        hub: KProgressHUD?,
        convertObject: ConvertObject<Int>
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
            .go(object : NetMainThreadCallback(hub) {
                override fun onResponse(response: Response?) {
                    val json = JSONObject(response?.body()?.string() ?: "")
                    val code = json.optInt("code")
                    convertObject.callback(code)
                }
            })
    }

}

