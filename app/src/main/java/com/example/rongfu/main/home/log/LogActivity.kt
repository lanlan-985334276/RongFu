package com.example.rongfu.main.home.log

import android.os.*
import android.util.Log
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.LogEntity
import com.example.rongfu.bean.User
import com.example.rongfu.main.free.FreeFragment
import com.example.rongfu.main.home.HomeFragment
import com.example.rongfu.main.adpater.ViewPagerAdpater
import com.example.rongfu.sensor.StepSensorBase
import com.example.rongfu.sensor.StepSensorPedometer
import com.example.rongfu.main.user.UserFragment
import com.example.rongfu.utils.GsonUtils
import com.example.rongfu.utils.OkHttpUtils
import com.example.rongfu.utils.SharedPrefsUtils
import com.example.rongfu.utils.ToastUtils
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_log.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.Call
import java.io.IOException
import java.sql.Timestamp

class LogActivity : BaseActivity(), LogContract.View {
    private lateinit var presenter: LogContract.Presenter
    private var user: User? = null

    override fun setPresenter(presenter: LogContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        LogPresenter(this).start()
    }

    override fun initView() {
        user = GsonUtils.json2Gson(SharedPrefsUtils.getString(this, "user", ""),
            object : TypeToken<User>() {})
    }

    override fun initLogic() {
        tv_submit.setOnClickListener {
            user?:return@setOnClickListener
            val title = et_title.text.toString()
            if (title.isNullOrEmpty()) {
                ToastUtils.showShort("标题不能为空！")
                return@setOnClickListener
            }
            val content = et_content.text.toString()
            if (content.isNullOrEmpty()) {
                ToastUtils.showShort("内容不能为空！")
                return@setOnClickListener
            }
            val log = LogEntity()
            log.epId = user!!.enterpriseId
            log.userId = user!!.userId
            log.addTime = Timestamp(System.currentTimeMillis())
            log.title = title
            log.content = content
            //上传
            val url = SharedPrefsUtils.getServiceUrl(this) + "/log/add"
            OkHttpUtils.postEnqueue(
                url,
                GsonUtils.gson2Json(log),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        runOnUiThread { ToastUtils.showShort("网络异常！") }
                    }

                    override fun success(call: Call, json: String) {
                        runOnUiThread {
                            val jsonBean =
                                GsonUtils.json2Gson(json, object : TypeToken<JsonBean<Log>>() {})
                            if (jsonBean.state == 1000) {
                                ToastUtils.showShort("添加成功！")
                                finish()
                            } else ToastUtils.showShort("网络异常！")
                        }
                    }
                })

        }
    }

}
