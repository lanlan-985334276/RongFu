package com.example.rongfu.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.User
import com.example.rongfu.main.MainActivity
import com.example.rongfu.register.RegisterActivity
import com.example.rongfu.service_url.ServiceUrlActivity
import com.example.rongfu.utils.*
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter

    private val TAG = "LoginActivity"
    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginPresenter(this).start()
        PermissionUtils.checkSdAndRequestPermission(this)
    }

    override fun initView() {

    }

    @SuppressLint("SetTextI18n")
    override fun initLogic() {
        tv_use_password.setOnClickListener {
            if (ll_send_code.visibility == View.VISIBLE) {
                et_code.setText("")
                ll_send_code.visibility = View.GONE
                et_password.visibility = View.VISIBLE
                tv_use_password.setText(R.string.username_code_login)
            } else {
                et_password.setText("")
                ll_send_code.visibility = View.VISIBLE
                et_password.visibility = View.GONE
                tv_use_password.setText(R.string.username_password_login)
            }
        }
        tv_forget_password.setOnClickListener {

        }
        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
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
                SharedPrefsUtils.getServiceUrl(this) + "/users/sendCode", GsonUtils.gson2Json(user),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        runOnUiThread {
                            ToastUtils.showShort("发送验证码失败！")
                        }
                    }

                    override fun success(call: Call, json: String) {
                        runOnUiThread {
                            ToastUtils.showShort("发送验证码成功！")
                        }
                    }
                }
            )
        }
        tv_login.setOnClickListener {
            val user = User()
            user.userName = et_username.text.toString()
            user.code = et_code.text.toString()
            user.password = et_password.text.toString()
            if (user.userName.isNullOrEmpty()) {
                ToastUtils.showShort("用户名不能为空！")
                return@setOnClickListener
            }
            if (ll_send_code.visibility == View.VISIBLE) {
                if (user.code.isNullOrEmpty()) {
                    ToastUtils.showShort("验证码不能为空！")
                    return@setOnClickListener
                }
            } else if (user.password.isNullOrEmpty()) {
                ToastUtils.showShort("密码不能为空！")
                return@setOnClickListener
            }
            val url =
                "/users/" + (if (user.password.isNullOrEmpty()) "loginByCode" else "loginApp")
            Log.i(TAG, url)
            OkHttpUtils.postEnqueue(
                SharedPrefsUtils.getServiceUrl(this) + url, GsonUtils.gson2Json(user),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        runOnUiThread {
                            ToastUtils.showShort("登录失败，请重试")
                        }
                    }

                    override fun success(call: Call, json: String) {
                        Log.i(TAG, Thread.currentThread().name)
                        runOnUiThread {
                            Log.i(TAG, Thread.currentThread().name)
                            Log.i(TAG, json)
                            val jsonBean = GsonUtils.json2Gson(json,
                                object : TypeToken<JsonBean<User>>() {})
                            Log.i(
                                TAG,
                                jsonBean.toString()
                            )
                            if (jsonBean.state == 1000) {
                                Log.i(TAG, jsonBean.data.toString())
                                ToastUtils.showShort("登录成功！")
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            } else ToastUtils.showShort(jsonBean.message)
                        }

                    }
                }
            )
        }
        tv_setting.setOnClickListener {
            val intent = Intent(this, ServiceUrlActivity::class.java)
            intent.putExtra("this", "Login")
            startActivity(intent)
        }
    }
}