package com.example.rongfu.utils

import android.util.Log
import com.example.rongfu.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object OkHttpUtils {

    private val okHttpClient = OkHttpClient()
    private val TAG = "OkHttpUtils"

    fun postEnqueue(url: String, json: String, callback: OkHttpCallback) {
        if (NetworkStateUtils.isNetworkConnected(ContextHolder.getContext()))
            object : Thread() {
                override fun run() {
                    super.run()
                    okHttpClient.newCall(
                        Request.Builder().url(url).post(
                            json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                        ).build()
                    ).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.i(TAG, "$TAG==failed==$e")
                            callback.failed(call, e)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val str = response.body!!.string()
                            Log.i(TAG, "$TAG==successful==$str")
                            callback.success(call, str)
                        }
                    })
                }
            }.start()
        else ToastUtils.showShort(R.string.network_status)
    }

    fun postEnqueue(url: String, callback: OkHttpCallback) {
        object : Thread() {
            override fun run() {
                super.run()
                okHttpClient.newCall(
                    Request.Builder().url(url).build()
                ).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.failed(call, e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val json = response.body!!.string()
                        callback.success(call, json)
                    }
                })
            }
        }.start()
    }

    fun getEnqueue(url: String, callback: OkHttpCallback) {
        object : Thread() {
            override fun run() {
                super.run()
                okHttpClient.newCall(
                    Request.Builder().url(url).get().build()
                ).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.failed(call, e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val json = response.body!!.string()
                        callback.success(call, json)
                    }
                })
            }
        }.start()
    }


    interface OkHttpCallback {
        fun failed(call: Call, e: IOException)
        fun success(call: Call, json: String)
    }
}