package com.example.rongfu

import android.app.Application
import com.example.rongfu.utils.ContextHolder

class RongFuApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ContextHolder.initContext(this)
    }
}