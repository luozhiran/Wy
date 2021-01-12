package com.lzr.wy.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.itg.lib_log.L
import com.lzr.lbase.BaseFragment
import com.lzr.wy.PROVIDER_AUTHORITY
import com.lzr.wy.databinding.UserFragmentBinding
import com.plugin.itg_util.InvokeSystemCameraUtils
import com.plugin.itg_util.Utils
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD


class UserFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel
    lateinit var hub: KProgressHUD
    lateinit var binder: UserFragmentBinding
    lateinit var imgTool: InvokeSystemCameraUtils

    val take_picturl = 11

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hub = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        binder = UserFragmentBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        imgTool = InvokeSystemCameraUtils.create(ItgOk.instance().application, PROVIDER_AUTHORITY)

        binder.photo.setOnClickListener {
            takePhoto()
        }
        val per = Utils.checkPermission(context, arrayOf(Manifest.permission.CAMERA))
        if (per != null && per.isNotEmpty()) {
            Utils.requestPermission(per, activity, 11)
        }

    }

    private fun takePhoto() {
        val dialogBox = AlertDialog.Builder(activity)
        dialogBox.setTitle("获取头像方式：")
        val items = arrayOf("从相册中选择", "拍照")
        dialogBox.setItems(items) { dialog, which ->
            Toast.makeText(activity, items[which], Toast.LENGTH_SHORT).show()
            when (which) {
                0 ->
                    imgTool.takePhotoSavePath(context?.cacheDir?.absolutePath)
                        .takePhotoPhotoName("default.jpeg")
                        .gotoGallery(activity, take_picturl)
                1 -> imgTool.takePhotoSavePath(context?.cacheDir?.absolutePath)
                    .takePhotoPhotoName("default.jpeg")
                    .gotoCameraForResult(activity, take_picturl)
            }

        }
        dialogBox.create().show()
    }

    override fun loadData() {
        L.e("获取用户信息")
        viewModel.getUser()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == take_picturl) {
            viewModel.uploadImg("")
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
                            viewModel.uploadImg(compressImgPath)
                        }
                    }

                })
        }
        L.e("UserFragment-------------${requestCode}")
    }


}


