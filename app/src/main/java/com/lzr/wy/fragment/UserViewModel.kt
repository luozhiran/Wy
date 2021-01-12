package com.lzr.wy.fragment

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.lzr.lbase.ConvertObject
import com.lzr.lbase.NetMainThreadCallback
import com.lzr.wy.BR
import com.lzr.wy.IP
import com.lzr.wy.R
import com.lzr.wy.bean.Repo
import com.lzr.wy.bean.User
import com.lzr.wy.bean.UserCenterItem
import com.plugin.mvvm.MergeObservableList
import com.plugin.mvvm.common.OnItemBindClass
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD
import okhttp3.Response
import java.io.IOException


class UserViewModel : ViewModel() {


    fun getUser(hub: KProgressHUD, convertObject: ConvertObject<User>) {
        ItgOk.instance()
            .url("${IP}/user")
            .go(object : NetMainThreadCallback(hub) {
                override fun onResponse(response: Response?) {
                    val str = response?.body()?.string()
                    val repo: Repo<User> = JSON.parseObject(str,
                        object : TypeReference<Repo<User>>(User::class.java) {})
                    if (repo.data == null) {
                        onFailure(null, IOException("数据异常"))
                    } else {
                        convertObject.callback(repo.data!!)
                    }
                }
            })
    }


    fun uploadImg(path: String, hub: KProgressHUD?) {
        hub?.show()
        ItgOk.instance()
            .url("${IP}/upload/head/photo")
            .method(ItgOk.POST)
            .addMultiFile("file", path)
            .go(object : NetMainThreadCallback(hub) {
                override fun onResponse(response: Response?) {


                }
            })
    }


    val items: ObservableList<UserCenterItem> = ObservableArrayList<UserCenterItem>()
    val headerFooterItems: MergeObservableList<Any>? = MergeObservableList()
    val multipleItems =
        OnItemBindClass<Any>().map(UserCenterItem::class.java, BR.item, R.layout.user_center_item)

    init {
        headerFooterItems?.insertList(items)
    }
    fun add(data: UserCenterItem) {
        items.add(data)
    }

    fun add(data: List<UserCenterItem>) {
        data?.let {
            items.addAll(it)
        }
    }
}
