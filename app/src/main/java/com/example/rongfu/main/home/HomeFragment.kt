package com.example.rongfu.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.bean.*
import com.example.rongfu.dialog.MyDialogFragment
import com.example.rongfu.main.adpater.StepAdapter
import com.example.rongfu.main.home.city.CityFragment
import com.example.rongfu.main.home.log.LogActivity
import com.example.rongfu.sensor.StepSensorBase
import com.example.rongfu.sensor.StepSensorPedometer
import com.example.rongfu.service_url.ServiceUrlActivity
import com.example.rongfu.utils.*
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.Call
import java.io.IOException

class HomeFragment : BaseFragment(), HomeContract.View, StepSensorBase.StepCallBack {

    private lateinit var presenter: HomeContract.Presenter

    private var user: User? = null
    private val list = mutableListOf<StepBean>()
    private lateinit var adapter: StepAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HomePresenter(this).start()
        StepSensorPedometer(context, this).registerStep()
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {
        //初始化计步排行榜
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StepAdapter(context!!, list)
        recyclerView.adapter = adapter
    }

    override fun initLogic() {
        getStep()
        getWeather()
        getUser()
        ll_sign_in.setOnClickListener {
            val dialog = MyDialogFragment()
            dialog.show(fragmentManager!!)
            val url = SharedPrefsUtils.getServiceUrl(context) + "/signIn/signIn"
            val sign = SignIn()
            if (user == null) {
                ToastUtils.showShort("数据加载中,请稍后！")
                return@setOnClickListener
            }
            sign.epid = user!!.enterpriseId
            sign.staffId = user!!.staffId
            OkHttpUtils.postEnqueue(
                url,
                GsonUtils.gson2Json(sign),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        activity!!.runOnUiThread {
                            dialog.dismiss()
                            ToastUtils.showShort("网络异常！")
                        }

                    }

                    override fun success(call: Call, json: String) {
                        activity!!.runOnUiThread {
                            dialog.dismiss()
                            val jsonBean =
                                GsonUtils.json2Gson(json, object : TypeToken<JsonBean<User>>() {})
                            if (jsonBean.state == 1000) {
                                ToastUtils.showShort("签到成功！")
                            } else ToastUtils.showShort("签到失败，请稍后重试！")
                        }
                    }
                })
        }
        ll_log.setOnClickListener {
            context!!.startActivity(Intent(context, LogActivity::class.java))
        }
        ll_weather.setOnClickListener {
            CityFragment(object : CityFragment.OnClickListener {
                override fun onClick() {
                    getWeather()
                }
            }).show(fragmentManager!!)
        }
        tv_loading.setOnClickListener {
            tv_loading.text = "正在加载中..."
            getWeather()
        }
    }

    private fun getWeather() {
        tv_loading.visibility = View.VISIBLE
        val cityInfo = getString(R.string.city_code)
        val city = SharedPrefsUtils.getString(activity, "city", "北京")
        val index = cityInfo.indexOf(city) + city.length + 1
        val cityCode = cityInfo.substring(index, index + 9)
        val path =
            "https://v0.yiketianqi.com/api?version=v9&appid=24932926&appsecret=e0a4Efzk&cityid=$cityCode"
        Log.i("HomeFragment", path)
        OkHttpUtils.getEnqueue(path, object : OkHttpUtils.OkHttpCallback {
            override fun failed(call: Call, e: IOException) {
                activity!!.runOnUiThread {
                    tv_loading.text = "网络异常!"
                    ToastUtils.showShort("请稍后重试！")
                }
            }

            override fun success(call: Call, json: String) {
                activity!!.runOnUiThread {
                    Log.i("HomeFragment", json)
                    tv_loading.visibility = View.GONE
                    try {
                        val weather =
                            GsonUtils.json2Gson(json, object : TypeToken<Weather>() {})
                        Log.i("HomeFragment", weather.toString())
                        tv_weather.text = weather.data[0].wea
                        tv_temp.text = weather.data[0].tem
                        tv_quality.text = "空气" + weather.aqi.air_level + " " + weather.aqi.air
                        tv_temp2.text = weather.data[0].tem2 + "℃~" + weather.data[0].tem1 + "℃"
                        tv_city.text = weather.city
                        tv_tips.text = weather.aqi.air_tips
                    } catch (e: Exception) {
                        tv_loading.text = "加载超时，请重试！"
                    }

                }
            }
        })
    }

    private fun getUser() {
        val url = SharedPrefsUtils.getServiceUrl(context) + "/index/getUser"
        val user = User()
        user.userId = SharedPrefsUtils.getInt(context, "userId", 0)
        OkHttpUtils.postEnqueue(
            url,
            GsonUtils.gson2Json(user),
            object : OkHttpUtils.OkHttpCallback {
                override fun failed(call: Call, e: IOException) {
                    activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                }

                override fun success(call: Call, json: String) {
                    activity!!.runOnUiThread {
                        val jsonBean =
                            GsonUtils.json2Gson(json, object : TypeToken<JsonBean<User>>() {})
                        if (jsonBean.state == 1000) {
                            Log.i("HomeFragment", jsonBean.data.staffId.toString())
                            this@HomeFragment.user = jsonBean.data
                            SharedPrefsUtils.putString(
                                context,
                                "user",
                                GsonUtils.gson2Json(jsonBean.data)
                            )
                        } else ToastUtils.showShort("网络异常！")
                    }
                }
            })
    }

    fun getStep() {
        val url = SharedPrefsUtils.getServiceUrl(context) + "/step/all"
        val step = StepBean()
        step.userId = SharedPrefsUtils.getInt(context, "userId", 0)
        OkHttpUtils.postEnqueue(
            url,
            GsonUtils.gson2Json(step),
            object : OkHttpUtils.OkHttpCallback {
                override fun failed(call: Call, e: IOException) {
                    activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                }

                override fun success(call: Call, json: String) {
                    activity!!.runOnUiThread {
                        var jsonBean: JsonBean<List<StepBean>>? = null
                        try {
                            jsonBean = GsonUtils.json2Gson(
                                json,
                                object : TypeToken<JsonBean<List<StepBean>>>() {})
                        } catch (e: Exception) {
                            context!!.startActivity(
                                Intent(
                                    activity!!,
                                    ServiceUrlActivity::class.java
                                ).putExtra("this", "main")
                            )
                        }
                        jsonBean ?: return@runOnUiThread
                        if (jsonBean.state == 1000) {
                            Log.i("HomeFragment", jsonBean.data.size.toString())
                            adapter.setData(jsonBean.data)
                        } else ToastUtils.showShort("网络异常！")
                    }
                }
            })
    }


    override fun Step(stepNum: Int) {
        var startStep = SharedPrefsUtils.getInt(context, "step", 0)
        var lastStep = 0
        Log.i("MainActivity", "startStep:$startStep lastStep:$lastStep")
        if (startStep == 0) {
            SharedPrefsUtils.putInt(context, "step", stepNum)
            startStep = stepNum
        }
        Log.i(
            "MainActivity",
            "startStep:${SharedPrefsUtils.getInt(context, "startStep", stepNum)} lastStep:$lastStep"
        )

        tv_step.text = (stepNum - startStep).toString()
        user ?: return
        Log.i("MainActivity", "step:$stepNum")
        if (stepNum - lastStep >= 10) {
            lastStep = stepNum
            val step = StepBean()
            step.userId = user!!.userId
            step.num = stepNum - startStep
            step.epId = user!!.enterpriseId
            step.userName = user!!.userName
            val url = SharedPrefsUtils.getServiceUrl(context) + "/step/update"
            OkHttpUtils.postEnqueue(
                url,
                GsonUtils.gson2Json(step),
                object : OkHttpUtils.OkHttpCallback {
                    override fun failed(call: Call, e: IOException) {
                        activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                    }

                    override fun success(call: Call, json: String) {
                        activity!!.runOnUiThread {
                            val jsonBean =
                                GsonUtils.json2Gson(json, object : TypeToken<JsonBean<User>>() {})
                            if (jsonBean.state != 1000) {
                                ToastUtils.showShort("本地步数上传异常")
                            }
                        }
                    }
                })
        }
    }
}