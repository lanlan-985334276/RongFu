package com.example.rongfu.utils

import com.example.rongfu.bean.JsonBean
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object GsonUtils {
    fun <T> json2Gson(json: String, typeToken: TypeToken<JsonBean<T>>): JsonBean<T> {
        val gson = GsonBuilder().setDateFormat("YYYY-MM-DD hh:mm:ss").create()
        return gson.fromJson(json, typeToken.type)
    }

    fun gson2Json(obj: Any): String {
        val gson = GsonBuilder().setDateFormat("YYYY-MM-DD hh:mm:ss").create()
        return gson.toJson(obj)
    }
}