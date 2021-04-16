package com.example.rongfu.main

import android.os.Bundle
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var persenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainPresenter(this).start()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.persenter = persenter
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initLogic() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
