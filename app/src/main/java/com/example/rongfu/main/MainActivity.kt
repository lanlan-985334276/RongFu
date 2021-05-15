package com.example.rongfu.main

import android.os.*
import android.util.Log
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.main.free.FreeFragment
import com.example.rongfu.main.home.HomeFragment
import com.example.rongfu.main.adpater.ViewPagerAdpater
import com.example.rongfu.sensor.StepSensorBase
import com.example.rongfu.sensor.StepSensorPedometer
import com.example.rongfu.main.user.UserFragment
import com.example.rongfu.utils.SharedPrefsUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : BaseActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainPresenter(this).start()
    }

    override fun initView() {
        val list = listOf<BaseFragment>(
            HomeFragment(), FreeFragment(), UserFragment()
        )
        viewPager.adapter = ViewPagerAdpater(supportFragmentManager, list)
    }

    override fun initLogic() {
        iv_home.setOnClickListener {
            viewPager.currentItem = 0
        }
        iv_free.setOnClickListener {
            viewPager.currentItem = 1
        }
        iv_user.setOnClickListener {
            viewPager.currentItem = 2
        }
    }

}
