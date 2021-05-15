package com.example.rongfu.main.free

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.Plan
import com.example.rongfu.bean.User
import com.example.rongfu.main.adpater.PlanAdpter
import com.example.rongfu.utils.GsonUtils
import com.example.rongfu.utils.OkHttpUtils
import com.example.rongfu.utils.SharedPrefsUtils
import com.example.rongfu.utils.ToastUtils
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_free.*
import okhttp3.Call
import java.io.IOException

class FreeFragment : BaseFragment(), FreeContract.View {

    private lateinit var presenter: FreeContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_free, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FreePresenter(this).start()
    }

    override fun setPresenter(presenter: FreeContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun initLogic() {
        //上传
        val user = GsonUtils.json2Gson(SharedPrefsUtils.getString(context, "user", ""),
            object : TypeToken<User>() {})
        if (user==null) return
        val url = SharedPrefsUtils.getServiceUrl(context) + "/plan/allNotFinished"
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
                            GsonUtils.json2Gson(json, object : TypeToken<JsonBean<List<Plan>>>() {})
                        if (jsonBean.state == 1000) {
                            val adpater = PlanAdpter(context!!, jsonBean.data)
                            recyclerView.adapter = adpater
                            adpater.setListner(object : PlanAdpter.OnClickListener {
                                override fun onClick(plan: Plan) {
                                    plan.userId = user.userId
                                    val url =
                                        SharedPrefsUtils.getServiceUrl(context) + "/plan/updateState"
                                    OkHttpUtils.postEnqueue(
                                        url,
                                        GsonUtils.gson2Json(plan),
                                        object : OkHttpUtils.OkHttpCallback {
                                            override fun failed(call: Call, e: IOException) {
                                                activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                                            }

                                            override fun success(call: Call, json: String) {
                                                activity!!.runOnUiThread {
                                                    val jsonBean =
                                                        GsonUtils.json2Gson(
                                                            json,
                                                            object :
                                                                TypeToken<JsonBean<User>>() {})
                                                    if (jsonBean.state == 1000) {
                                                        ToastUtils.showShort("提交完成！")
                                                        initLogic()
                                                    } else ToastUtils.showShort("网络异常！!")
                                                }
                                            }
                                        })
                                }
                            })
                        } else ToastUtils.showShort("网络异常！!")
                    }
                }
            })
    }
}