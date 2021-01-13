package com.lzr.wy.activity


import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.itg.lib_log.L
import com.lzr.lbase.LinDividerItemDecoration

import com.lzr.wy.R
import com.lzr.wy.databinding.ActivitySettingBinding
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
    }


    fun quit(view: View) {

        L.e("00000000000000")
    }
}