package com.lzr.wy.fragment

import androidx.lifecycle.ViewModel
import com.itg.lib_log.L
import com.plugin.okhttp_lib.okhttp.ItgOk
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback

class UserViewModel : ViewModel() {


    fun getUser() {
        ItgOk.instance()
            .url("http://192.168.40.163:8081/user")
            .go(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    L.e(response.body()?.string())
                }

            })
    }
}
