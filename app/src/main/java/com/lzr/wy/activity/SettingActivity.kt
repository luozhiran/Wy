package com.lzr.wy.activity


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lzr.lbase.LinDividerItemDecoration
import com.lzr.wy.R
import com.lzr.wy.USER_XML
import com.lzr.wy.databinding.ActivitySettingBinding
import com.lzr.wy.login.LoginActivity
import com.yk.base.BaseActivity

class SettingActivity : BaseActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binder: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binder.model = viewModel

        viewModel.createSettingItem()
        binder.recyclerView.addItemDecoration(LinDividerItemDecoration(Color.parseColor("#cccccc")))
        viewModel.bindItemEvent(this)
        binder.head.setSrcOnClick { finish() }
    }


    fun quit(view: View) {
        val sh = getSharedPreferences(USER_XML, Context.MODE_PRIVATE)
        sh.edit().clear().apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}