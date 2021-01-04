package com.lzr.wy

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lzr.wy.databinding.ActivityMainBinding
import com.lzr.wy.fragment.ActivityFragment
import com.lzr.wy.fragment.HomeFragment
import com.lzr.wy.fragment.OwnFloorFragment
import com.lzr.wy.fragment.UserFragment
import com.yk.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binder: ActivityMainBinding
    lateinit var model: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        binder.model = model
        model.insertFragment(
            HomeFragment.newInstance(),
            OwnFloorFragment.newInstance(),
            ActivityFragment.newInstance(),
            UserFragment.newInstance()
        )
    }

}
