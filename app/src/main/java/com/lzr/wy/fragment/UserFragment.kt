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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hub = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        binder = UserFragmentBinding.inflate(inflater, container, false)
        binder.event = this
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        binder.model = viewModel
        viewModel.bindItemEvent(this)
        binder.photo.setOnClickListener { activity?.let { viewModel.takePhoto(it) } }
        val per = Utils.checkPermission(context, arrayOf(Manifest.permission.CAMERA))
        if (per != null && per.isNotEmpty()) {
            Utils.requestPermission(per, activity, 11)
        }
        binder.recycler.addItemDecoration(GridDividerItemDecoration(Color.parseColor("#f1f1f1"), 1))

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handlePhoto(requestCode, resultCode, data, hub)
        L.e("UserFragment-------------${requestCode}")
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.tab1 -> {
            }
            R.id.tab2 -> {
            }
            R.id.tab3 -> {
            }
            R.id.tab4 -> {
                startActivity(Intent(context, SettingActivity::class.java))
            }
        }
    }

    fun onClick(v: View?, item: UserCenterItem) {
        L.e(item.name)
        when (item.name) {
            "地址" -> {
            }
            "优惠卷" -> {
            }
            "天气" -> {
            }
            "分享" -> {
            }
            "发布" -> {
            }
            "记录" -> {
            }
        }
    }


    override fun loadData() {
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

}


