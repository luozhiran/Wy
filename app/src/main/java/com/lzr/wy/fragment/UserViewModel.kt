package com.lzr.wy.fragment

import android.os.Environment
import androidx.lifecycle.ViewModel
import com.itg.lib_log.L
import com.lzr.lbase.NetCallback
import com.lzr.wy.IP
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD
import okhttp3.Call
import okhttp3.Response
import java.io.File
import java.io.IOException

class UserViewModel : ViewModel() {


    fun getUser() {
        ItgOk.instance()
            .url("${IP}/user")

            .go(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    L.e(response.body()?.string())
                }

            })
    }


    fun uploadImg(path: String,hub:KProgressHUD?) {
        hub?.show()
        ItgOk.instance()
            .url("${IP}/upload/head/photo")
            .method(ItgOk.POST)
            .addMultiFile("file", File("${Environment.getExternalStorageDirectory()}/logo.png"))
            .go(object : NetCallback(){
                override fun onResponse(response: Response?) {

                }
            })
    }
}
