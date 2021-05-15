package com.example.rongfu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.login.LoginActivity
import com.example.rongfu.main.MainActivity
import com.example.rongfu.service_url.ServiceUrlActivity
import com.example.rongfu.utils.ContextHolder
import com.example.rongfu.utils.PermissionUtils
import com.example.rongfu.utils.SharedPrefsUtils

class StartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Log.i("StartActivity", "start")
        start()
    }

    private fun start() {
        Handler().postDelayed({
            val url = SharedPrefsUtils.getServiceUrl(this)
            val userId = SharedPrefsUtils.getInt(this, "userId", 0)
            if (url.isNullOrEmpty()) {
                startActivity(Intent(this, ServiceUrlActivity::class.java))
            } else if (userId == 0)
                startActivity(Intent(this, LoginActivity::class.java))
            else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 1000)
    }
}