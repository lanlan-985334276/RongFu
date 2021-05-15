package com.example.rongfu.main.free

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.bean.JsonBean
import com.example.rongfu.bean.Plan
import com.example.rongfu.bean.PlanItem
import com.example.rongfu.bean.User
import com.example.rongfu.dialog.MyDialogFragment
import com.example.rongfu.main.adpater.PlanAdapter
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
    private var adpater: PlanAdapter? = null
    private val list = mutableListOf<Plan>()
    private var user: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_free, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            adpater ?: return
            getPlan()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FreePresenter(this).start()
        user = GsonUtils.json2Gson(SharedPrefsUtils.getString(context, "user", ""),
            object : TypeToken<User>() {})
    }

    override fun setPresenter(presenter: FreeContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adpater = PlanAdapter(context!!, list)
        recyclerView.adapter = adpater
    }

    override fun initLogic() {
        getPlan()
        adpater!!.setListner(object : PlanAdapter.OnClickListener {
            override fun onClick(plan: Plan) {
                user ?: return
                val dialog = MyDialogFragment()
                dialog.show(fragmentManager!!)
                plan.userId = user!!.userId
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
                                dialog.dismiss()
                                val jsonBean =
                                    GsonUtils.json2Gson(
                                        json,
                                        object :
                                            TypeToken<JsonBean<User>>() {})
                                if (jsonBean.state == 1000) {
                                    ToastUtils.showShort("提交完成！")
                                    initLogic()
                                } else ToastUtils.showShort("提交失败，请重试！!")
                            }
                        }
                    })
            }
        })
    }

    private fun getPlan() {
        showLoadingView()
        user ?: return
        val url = SharedPrefsUtils.getServiceUrl(context) + "/plan/allNotFinished"
        OkHttpUtils.postEnqueue(
            url,
            GsonUtils.gson2Json(user!!),
            object : OkHttpUtils.OkHttpCallback {
                override fun failed(call: Call, e: IOException) {
                    hideLoadingView()
                    activity!!.runOnUiThread { ToastUtils.showShort("网络异常！") }
                }

                override fun success(call: Call, json: String) {
                    activity!!.runOnUiThread {
                        hideLoadingView()
                        val jsonBean =
                            GsonUtils.json2Gson(json, object : TypeToken<JsonBean<List<Plan>>>() {})
                        if (jsonBean.state == 1000) {
                            adpater ?: return@runOnUiThread
                            if (jsonBean.data.size == 0) tv_noData.visibility = View.VISIBLE
                            else tv_noData.visibility = View.GONE
                            adpater!!.setData(jsonBean.data)
                        } else ToastUtils.showShort("网络异常！!")
                    }
                }
            })
    }

    private fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideLoadingView() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}