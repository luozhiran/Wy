package com.lzr.wy.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.lzr.lbase.NetMainThreadCallback
import com.lzr.lbase.UINCallback
import com.lzr.wy.BR
import com.lzr.wy.IP
import com.lzr.wy.PROVIDER_AUTHORITY
import com.lzr.wy.R
import com.lzr.wy.bean.User
import com.lzr.wy.bean.UserCenterItem
import com.plugin.itg_util.InvokeSystemCameraUtils
import com.plugin.mvvm.MergeObservableList
import com.plugin.mvvm.common.OnItemBindClass
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD


class UserViewModel : ViewModel() {

    val take_picturl = 11

    val imgTool: InvokeSystemCameraUtils =
        InvokeSystemCameraUtils.create(ItgOk.instance().application, PROVIDER_AUTHORITY)


    fun getUser(hub: KProgressHUD, uinCallback: UINCallback<User>) {
        ItgOk.instance()
            .url("${IP}/login-register-service/user")
            .go(object : NetMainThreadCallback<User>(hub,User::class.java) {
                override fun onFailure(code: Int, error: String?) {

                }

                override fun onResponse(t: User?, msg: String?) {
                    uinCallback.callback(t)
                }

            })
    }


    fun uploadImg(path: String, hub: KProgressHUD?) {
        hub?.show()
        ItgOk.instance()
            .url("${IP}/upload/head/photo")
            .method(ItgOk.POST)
            .addMultiFile("file", path)
            .go(object : NetMainThreadCallback<String>(hub,String::class.java) {
                override fun onFailure(code: Int, error: String?) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(t: String?, msg: String?) {
                    TODO("Not yet implemented")
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

    fun bindItemEvent(event: UserFragment) {
        multipleItems.bindExtra(BR.event, event)
    }

    fun add(data: UserCenterItem) {
        items.add(data)
    }

    fun add(data: List<UserCenterItem>) {
        data?.let {
            items.addAll(it)
        }
    }


    fun takePhoto(context: Activity) {
        val dialogBox = AlertDialog.Builder(context)
        dialogBox.setTitle("获取头像方式：")
        val items = arrayOf("从相册中选择", "拍照")
        dialogBox.setItems(items) { dialog, which ->
            Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show()
            when (which) {
                0 ->
                    imgTool.takePhotoSavePath(context.cacheDir?.absolutePath)
                        .takePhotoPhotoName("default.jpeg")
                        .gotoGallery(context, take_picturl)
                1 -> imgTool.takePhotoSavePath(context.cacheDir?.absolutePath)
                    .takePhotoPhotoName("default.jpeg")
                    .gotoCameraForResult(context, take_picturl)
            }

        }
        dialogBox.create().show()
    }

    fun handlePhoto(requestCode: Int, resultCode: Int, data: Intent?, hub: KProgressHUD) {
        if (requestCode == take_picturl) {
            uploadImg("${Environment.getExternalStorageDirectory()}/logo.png", hub)
            imgTool.luBanCompress(
                requestCode,
                resultCode,
                data,
                object : InvokeSystemCameraUtils.OnGalleryResultCallback {
                    override fun onResultFromCrop(path: String?) {

                    }

                    override fun onResultPhone(
                        uri: Uri?,
                        orgImgPath: String?,
                        compressImgPath: String?
                    ) {
                        if (TextUtils.isEmpty(compressImgPath)) return
                        compressImgPath?.let {
                            uploadImg(compressImgPath, hub)
                        }
                    }

                })
        }
    }
}
