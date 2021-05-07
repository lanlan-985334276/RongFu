package com.example.rongfu.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object OkHttpUtils {

    private val okHttpClient = OkHttpClient()

    public fun postEnqueue(url: String, json: String, callback: Callback) {
        object : Thread() {
            override fun run() {
                super.run()
                okHttpClient.newCall(
                    Request.Builder().url(url).post(
                        json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    ).build()
                ).enqueue(callback)
            }
        }.start()
    }

    public fun postEnqueue(url: String, callback: OkHttpCallback) {
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
                        callback.success(call, response)
                    }
                })
            }
        }.start()
    }

    interface OkHttpCallback {
        fun failed(call: Call, e: IOException)
        fun success(call: Call, response: Response)
    }
}