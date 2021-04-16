package com.example.rongfu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.login.LoginActivity

class StartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_start)
        Handler().postDelayed({
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 1000)
    }
}