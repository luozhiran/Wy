package com.lzr.wy.login

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lzr.wy.R
import com.lzr.wy.databinding.ActivityLoginBinding
import com.plugin.widget.dialog.KProgressHUD
import com.yk.base.BaseActivity
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginActivity : BaseActivity(), View.OnClickListener {

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
        hub = KProgressHUD.create(this).setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE);

    }

    override fun onClick(v: View?) {
        v?.let {
            binder.apply {
                when (it.id) {
                    R.id.login -> {
                       login.isSelected = true
                        register.isSelected = false
                    }
                    R.id.register -> {
                        login.isSelected = false
                        register.isSelected = true
                        loginParamView.switchPan()
                    }
                    R.id.loginBtn -> {
                        if (login.isSelected) {
                            loginParamView.apply {
                                model.login(
                                    phone,
                                    pwd,
                                    hub,
                                    object : OnNotifyUi<Response> {
                                        override fun onNotify(data: Response) {

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
                                    object : OnNotifyUi<Response> {
                                        override fun onNotify(data: Response) {

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
