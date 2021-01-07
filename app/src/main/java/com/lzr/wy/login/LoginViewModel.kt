package com.lzr.wy.login

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.itg.lib_log.L
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginViewModel : ViewModel() {

    private val handler: Handler = Handler(Looper.getMainLooper())

    fun login(name: String, pwd: String, hub: KProgressHUD, notifyUi: OnNotifyUi<Response>) {
        hub?.show()
        ItgOk
            .instance()
            .url("http://192.168.40.163:8081/login")
            .method(ItgOk.GET)
            .addParams("name", name)
            .addParams("pwd", pwd)
            .go(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.post {
                        Toast.makeText(ItgOk.instance().application, e.message, Toast.LENGTH_SHORT)
                            .show()
                        hub?.dismiss()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post { hub?.dismiss() }
                    notifyUi.onNotify(response)
                    L.e(response.body()?.string())
                }

            })

    }

    fun register(
        phone: String,
        pwd: String,
        email: String,
        code: String,
        hub: KProgressHUD,
        notifyUi: OnNotifyUi<Response>
    ) {
        hub?.show()
        ItgOk
            .instance()
            .url("http://192.168.40.163:8081/register")
            .method(ItgOk.POST)
            .addParams("phone", phone)
            .addParams("pwd", pwd)
            .addParams("email", email)
            .addParams("vcode", code)
            .go(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.post {
                        Toast.makeText(ItgOk.instance().application, e.message, Toast.LENGTH_SHORT)
                            .show()
                        hub?.dismiss()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post { hub?.dismiss() }
                    notifyUi.onNotify(response)
                }

            })
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(null)
    }

}


interface OnNotifyUi<T> {
    fun onNotify(data: T);
}