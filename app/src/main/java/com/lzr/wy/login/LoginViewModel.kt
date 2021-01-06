package com.lzr.wy.login

import android.os.Handler
import android.os.Looper
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
            .url("http://localhost:8081/login")
            .method(ItgOk.GET)
            .addParams("name", name)
            .addParams("pwd", pwd)
            .go(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.post { hub?.dismiss() }
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
            .url("http://localhost:8081/register")
            .method(ItgOk.POST)
            .addParams("phone", phone)
            .addParams("pwd", pwd)
            .addParams("email", email)
            .addParams("code", code)
            .go(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.post { hub?.dismiss() }
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post { hub?.dismiss() }
                    notifyUi.onNotify(response)
                    L.e(response.body()?.string())
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