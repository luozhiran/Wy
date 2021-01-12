package com.lzr.wy.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.itg.lib_log.L
import com.lzr.lbase.ConvertObject
import com.lzr.lbase.WrapBaseActivity
import com.lzr.lview.LoginView
import com.lzr.wy.MainActivity
import com.lzr.wy.R
import com.lzr.wy.databinding.ActivityLoginBinding
import com.plugin.itg_util.Utils
import com.plugin.widget.dialog.KProgressHUD
import com.yk.base.BaseActivity
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginActivity : WrapBaseActivity(), View.OnClickListener {

    private lateinit var binder: ActivityLoginBinding
    private lateinit var model: LoginViewModel
    private lateinit var hub: KProgressHUD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binder.login.setOnClickListener(this)
        binder.register.setOnClickListener(this)
        binder.loginBtn.setOnClickListener(this)
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        hub = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onClick(v: View?) {
        v?.let {
            binder.apply {
                when (it.id) {
                    R.id.login -> {
                        login.isSelected = true
                        register.isSelected = false
                        loginParamView.switchPan(LoginView.LOGIN)
                    }
                    R.id.register -> {
                        login.isSelected = false
                        register.isSelected = true
                        loginParamView.switchPan(LoginView.REGISTER)
                    }
                    R.id.loginBtn -> {
                        if (login.isSelected) {
                            loginParamView.apply {
                                model.login(
                                    phone,
                                    pwd,
                                    hub,
                                    object : ConvertObject<Int> {
                                        override fun callback(data: Int) {
                                            if (data == 1) {
                                                startActivity(
                                                    Intent(
                                                        this@LoginActivity,
                                                        MainActivity::class.java
                                                    )
                                                )
                                                finish()
                                            }
                                        }
                                    })
                            }
                        } else {
                            loginParamView.apply {
                                model.register(
                                    phone,
                                    pwd,
                                    email,
                                    vcode,
                                    hub,
                                    object : ConvertObject<Int> {
                                        override fun callback(data: Int) {
                                            switchPan(LoginView.LOGIN)
                                        }
                                    })
                            }
                        }
                    }
                    else -> {
                    }
                }
            }

        }
    }


}
