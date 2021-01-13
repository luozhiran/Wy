package com.lzr.wy.activity

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.lzr.wy.BR
import com.lzr.wy.R
import com.lzr.wy.bean.SettingItem
import com.lzr.wy.bean.UserCenterItem
import com.plugin.itg_util.ApkUtils
import com.plugin.mvvm.MergeObservableList
import com.plugin.mvvm.common.OnItemBind
import com.plugin.mvvm.common.OnItemBindClass
import com.plugin.okhttp_lib.okhttp.ItgOk

class SettingViewModel : ViewModel() {

    val itemList: ObservableList<Any> = ObservableArrayList<Any>()
    val totalList: MergeObservableList<Any> = MergeObservableList()
    val multipleItems: OnItemBindClass<Any> =
        OnItemBindClass<Any>().map(SettingItem::class.java, BR.item, R.layout.setting_item)
            .map(Any::class.java, BR.item, R.layout.simple_view)


    init {
        totalList.insertList(itemList)

    }


    fun createSettingItem() {
        itemList.add(SettingItem("版本号：", ApkUtils.getApkVersionName(ItgOk.instance().application)))
        itemList.add(SettingItem("黑夜模式：", "关"))
        itemList.add(SettingItem("缓存：", "20M"))
        itemList.add(Any())
    }

    fun bindItemEvent(click: SettingActivity) {
        multipleItems.bindExtra(BR.click, click)
    }


}