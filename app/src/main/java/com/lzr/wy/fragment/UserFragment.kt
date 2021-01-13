package com.lzr.wy.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.itg.lib_log.L
import com.lzr.lbase.BaseFragment
import com.lzr.lbase.ConvertObject
import com.lzr.lbase.GridDividerItemDecoration
import com.lzr.wy.PROVIDER_AUTHORITY
import com.lzr.wy.R
import com.lzr.wy.activity.SettingActivity
import com.lzr.wy.bean.User
import com.lzr.wy.bean.UserCenterItem
import com.lzr.wy.databinding.UserFragmentBinding
import com.plugin.itg_util.FileIoUtils
import com.plugin.itg_util.InvokeSystemCameraUtils
import com.plugin.itg_util.Utils
import com.plugin.okhttp_lib.okhttp.ItgOk
import com.plugin.widget.dialog.KProgressHUD
import kotlinx.android.synthetic.main.user_fragment.view.*
import java.io.File


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
        binder.model = viewModel
        imgTool = InvokeSystemCameraUtils.create(ItgOk.instance().application, PROVIDER_AUTHORITY)

        binder.photo.setOnClickListener {
            takePhoto()
        }
        val per = Utils.checkPermission(context, arrayOf(Manifest.permission.CAMERA))
        if (per != null && per.isNotEmpty()) {
            Utils.requestPermission(per, activity, 11)
        }
        binder.recycler.addItemDecoration(GridDividerItemDecoration(Color.parseColor("#f1f1f1"), 1))


        binder.tab1.setOnClickListener{

        }
        binder.tab2.setOnClickListener{

        }
        binder.tab3.setOnClickListener{

        }
        binder.tab4.setOnClickListener{
            startActivity(Intent(context, SettingActivity::class.java))
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
        viewModel.getUser(hub, object : ConvertObject<User> {
            override fun callback(t: User) {
                L.e(t.picture)
                context?.let {
                    Glide.with(it).load(t.picture).into(binder.photo)
                    binder.userName.text = t.name
                }

            }
        })
        val centerItemString = FileIoUtils.getAssetsFile("user_center.json", context)
        val list = JSON.parseArray(centerItemString, UserCenterItem::class.java)
        viewModel.add(list)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == take_picturl) {
            viewModel.uploadImg("${Environment.getExternalStorageDirectory()}/logo.png", hub)
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
                            viewModel.uploadImg(compressImgPath, hub)
                        }
                    }

                })
        }
        L.e("UserFragment-------------${requestCode}")
    }


}


