package com.example.rongfu.main.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.User
import com.example.rongfu.login.LoginActivity
import com.example.rongfu.service_url.ServiceUrlActivity
import com.example.rongfu.utils.GsonUtils
import com.example.rongfu.utils.OkHttpUtils
import com.example.rongfu.utils.SharedPrefsUtils
import com.example.rongfu.utils.ToastUtils
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.et_username
import okhttp3.Call
import java.io.IOException

class UserFragment : BaseFragment(), UserContract.View {

    private lateinit var presenter: UserContract.Presenter
    private var user: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UserPresenter(this).start()
    }

    override fun setPresenter(presenter: UserContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {
        user = GsonUtils.json2Gson(SharedPrefsUtils.getString(context, "user", ""),
            object : TypeToken<User>() {})
    }

    override fun initLogic() {
        tv_edit.setOnClickListener {
            tv_return.visibility = View.GONE
            tv_setting.visibility = View.GONE
            ll_edit.visibility = View.VISIBLE
            user ?: return@setOnClickListener
            et_username.setText(user!!.userName)
            et_email.setText(user!!.email)
        }
        tv_setting.setOnClickListener {
            startActivity(Intent(activity!!, ServiceUrlActivity::class.java))
        }
        tv_cancel.setOnClickListener {
            tv_return.visibility = View.VISIBLE
            tv_setting.visibility = View.VISIBLE
            ll_edit.visibility = View.GONE
        }
        tv_return.setOnClickListener {
            SharedPrefsUtils.putInt(context, "userId", 0)
            SharedPrefsUtils.putString(context, "user", "")
            startActivity(Intent(activity!!, LoginActivity::class.java))
            activity!!.finish()
        }
        tv_sure.setOnClickListener {
            user ?: return@setOnClickListener
            val username = et_username.text.toString()
            if (username.isNullOrEmpty()) {
                ToastUtils.showShort("用户名不能为空！")
                return@setOnClickListener
            }
            val email = et_email.text.toString()
            if (email.isNullOrEmpty()) {
            }
            val password0 = et_password0.text.toString()
            val password1 = et_password1.text.toString()
            val password2 = et_password2.text.toString()
            if (!password0.isNullOrEmpty() || !password1.isNullOrEmpty() || !password2.isNullOrEmpty()) {
                Log.i("UserFragment",password0+" "+password1+" "+password2)
                if (password0.isNullOrEmpty()) {
                    ToastUtils.showShort("原始密码不能为空！")
                    return@setOnClickListener
                }
                if (password1.isNullOrEmpty()) {
                    ToastUtils.showShort("新密码不能为空！")
                    return@setOnClickListener
                }
                if (password2 != password1) {
                    ToastUtils.showShort("两次输入的密码不一致！")
                    return@setOnClickListener
                }
                user!!.password0 = password0
                user!!.password1 = password1
            }
            user!!.userName = username
            user!!.email = email
            val url = SharedPrefsUtils.getServiceUrl(context) + "/users/updateUser"
            OkHttpUtils.postEnqueue(
                url,
                GsonUtils.gson2Json(user!!),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                    }

                    override fun success(call: Call, json: String) {
                        activity!!.runOnUiThread {
                            val jsonBean =
                                GsonUtils.json2Gson(json, object : TypeToken<JsonBean<Log>>() {})
                            if (jsonBean.state == 1000) {
                                ToastUtils.showShort("修改成功！")
                                SharedPrefsUtils.putString(
                                    context,
                                    "user",
                                    GsonUtils.gson2Json(user!!)
                                )
                                ll_edit.visibility = View.GONE
                                tv_setting.visibility = View.VISIBLE
                                tv_return.visibility = View.VISIBLE
                                et_username.setText("")
                                et_email.setText("")
                                et_password0.setText("")
                                et_password1.setText("")
                                et_password2.setText("")
                            } else ToastUtils.showShort("网络异常！")
                        }
                    }
                })
        }
    }
}