package com.lzr.wy.first

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.lzr.lbase.WrapBaseActivity
import com.lzr.wy.MainActivity
import com.lzr.wy.R
import com.lzr.wy.USER_XML
import com.lzr.wy.databinding.ActivityFirstBinding
import com.lzr.wy.login.LoginActivity

class FirstActivity : WrapBaseActivity() {

    lateinit var binder: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_first)
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val sharedPreferences = getSharedPreferences(USER_XML, Context.MODE_PRIVATE)
        if (TextUtils.isEmpty(sharedPreferences.getString("token", ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}
