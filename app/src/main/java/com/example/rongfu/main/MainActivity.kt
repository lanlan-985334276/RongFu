package com.example.rongfu.main

import android.os.Bundle
import android.util.Log
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity
import com.example.rongfu.base.page.BaseFragment
import com.example.rongfu.free.FreeFragment
import com.example.rongfu.home.HomeFragment
import com.example.rongfu.main.adpater.ViewPagerAdpater
import com.example.rongfu.user.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

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
