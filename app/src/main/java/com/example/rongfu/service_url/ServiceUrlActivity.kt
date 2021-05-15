package com.example.rongfu.service_url

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.login.LoginActivity
import com.example.rongfu.utils.PermissionUtils
import com.example.rongfu.utils.SharedPrefsUtils
import com.example.rongfu.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_service_url.*
import java.util.regex.Pattern

class ServiceUrlActivity : BaseActivity(), ServiceUrlContract.View {

    private val TAG = "ServiceUrlActivity"
    private lateinit var presenter: ServiceUrlContract.Presenter
    override fun setPresenter(presenter: ServiceUrlContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_url)
        ServiceUrlPresenter(this).start()
        PermissionUtils.checkSdAndRequestPermission(this)
    }

    override fun initView() {
        et_url.setText(SharedPrefsUtils.getServiceUrl(this))
    }

    override fun initLogic() {
        tv_set.setOnClickListener {
            if (et_url.text.isNullOrEmpty()) {
                ToastUtils.showShort("服务器地址不能为空！")
                return@setOnClickListener
            }
            if (!Pattern.compile("(((https|http)?://)?([a-z0-9]+[.])|(www.))\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)")
                    .matcher(et_url.text.toString()).find()
            ) {
                ToastUtils.showShort("格式错误，请输入正确的链接格式！")
                return@setOnClickListener
            }
            Log.i(TAG, "服务器地址：${tv_set.text.toString()}")
            SharedPrefsUtils.putServiceUrl(this, et_url.text.toString())
            if (intent.getStringExtra("this").isNullOrEmpty())
                startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}