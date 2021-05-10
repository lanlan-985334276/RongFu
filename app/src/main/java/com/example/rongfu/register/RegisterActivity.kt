package com.example.rongfu.register

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.User
import com.example.rongfu.utils.*
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class RegisterActivity : BaseActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterContract.Presenter
    private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        RegisterPresenter(this).start()

    }

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {

    }

    override fun initLogic() {
        btn_send_code.setOnClickListener {
            val user = User()
            user.userName = et_username.text.toString()
            if (user.userName.isNullOrEmpty()) {
                ToastUtils.showShort("用户名不能为空！")
                return@setOnClickListener
            }
            btn_send_code.isClickable = false
            //倒计时
            object : CountDownTimer(60 * 1000, 1000) {
                override fun onFinish() {
                    btn_send_code.isClickable = true
                    btn_send_code.setText(R.string.send_code)
                }

                override fun onTick(millisUntilFinished: Long) {
                    btn_send_code.setText("${millisUntilFinished / 1000}s")
                }
            }.start()
            Log.i(TAG, SharedPrefsUtils.getServiceUrl(this) + "/users/sendCode")
            OkHttpUtils.postEnqueue(
                SharedPrefsUtils.getServiceUrl(this) + "/users/sendCode",
                GsonUtils.gson2Json(user),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        runOnUiThread {
                            ToastUtils.showShort("发送验证码失败！")
                        }
                    }

                    override fun success(call: Call, json: String) {
                        runOnUiThread {
                            if (GsonUtils.json2Gson(json,
                                    object : TypeToken<JsonBean<User>>() {}).state == 1000)
                                ToastUtils.showShort("发送验证码成功！")
                            else ToastUtils.showShort("发送验证码失败！")
                        }
                    }
                }
            )

        }
        tv_cancel.setOnClickListener {
            finish()
        }
        val user = User()
        tv_next.setOnClickListener {
            when {
                ll_input_view.visibility == View.VISIBLE -> {
                    val userName = et_username.text.toString()
                    if (userName.isNullOrEmpty()) {
                        ToastUtils.showShort("用户名不能为空！")
                        return@setOnClickListener
                    }
                    user.userName = userName
                    val code = et_code.text.toString()
                    if (code.isNullOrEmpty()) {
                        ToastUtils.showShort("验证码不能为空！")
                        return@setOnClickListener
                    }
                    user.code = code
                    val password = et_password.text.toString()
                    if (password.isNullOrEmpty()) {
                        ToastUtils.showShort("密码不能为空！")
                        return@setOnClickListener
                    }
                    val password2 = et_password_again.text.toString()

                    if (password2.isNullOrEmpty() || !password2.equals(password)) {
                        ToastUtils.showShort("输入的密码不一致！")
                        return@setOnClickListener
                    }
                    user.password = password
                    OkHttpUtils.postEnqueue(SharedPrefsUtils.getServiceUrl(this) + "/users/equalsCode",
                        GsonUtils.gson2Json(user),
                        object : OkHttpUtils.OkHttpCallback {
                            override fun failed(call: Call, e: IOException) {
                                runOnUiThread {
                                    ToastUtils.showShort("请稍后重试!")
                                }
                            }

                            override fun success(call: Call, json: String) {
                                runOnUiThread {
                                    val jsonBean = GsonUtils.json2Gson(json,
                                        object : TypeToken<JsonBean<User>>() {})
                                    if (jsonBean.state == 1000) {
                                        ll_input_view.visibility = View.GONE
                                        ll_chose.visibility = View.VISIBLE
                                        et_enterprise.visibility = View.GONE
                                        ll_is_next.visibility = View.GONE
                                    } else ToastUtils.showShort(jsonBean.message)
                                }
                            }
                        })
                }
                ll_chose.visibility == View.VISIBLE -> {
                    et_enterprise.visibility = View.VISIBLE
                    ll_is_next.visibility = View.VISIBLE
                }
                et_enterprise.visibility == View.VISIBLE -> {
                    val epName = et_enterprise.text.toString()
                    if (epName.isNullOrEmpty()) {
                        ToastUtils.showShort("企业名不能为空！")
                        return@setOnClickListener
                    }
                    user.epName = epName
                    OkHttpUtils.postEnqueue(SharedPrefsUtils.getServiceUrl(this) + "/users/regEp",
                        GsonUtils.gson2Json(user),
                        object : OkHttpUtils.OkHttpCallback {
                            override fun failed(call: Call, e: IOException) {
                                runOnUiThread {
                                    ToastUtils.showShort("请稍后重试!")
                                }
                            }

                            override fun success(call: Call, json: String) {
                                runOnUiThread {
                                    val jsonBean = GsonUtils.json2Gson(json,
                                        object : TypeToken<JsonBean<User>>() {})
                                    if (jsonBean.state == 1000) {
                                        ToastUtils.showShort("注册成功！")
                                        finish()
                                    } else ToastUtils.showShort(jsonBean.message)
                                }
                            }
                        })
                }
            }
        }
        fl_became_enterprise.setOnClickListener {
            ll_chose.visibility = View.GONE
            ll_is_next.visibility = View.VISIBLE
            et_enterprise.visibility = View.VISIBLE
        }
        fl_became_other.setOnClickListener {
            OkHttpUtils.postEnqueue(SharedPrefsUtils.getServiceUrl(this) + "/users/regOther",
                GsonUtils.gson2Json(user),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        runOnUiThread {
                            ToastUtils.showShort("请稍后重试!")
                        }
                    }

                    override fun success(call: Call, json: String) {
                        runOnUiThread {
                            val jsonBean = GsonUtils.json2Gson(json,
                                object : TypeToken<JsonBean<User>>() {})
                            if (jsonBean.state == 1000) {
                                ToastUtils.showShort("注册成功！")
                                finish()
                            } else ToastUtils.showShort(jsonBean.message)
                        }
                    }
                })

        }
    }
}