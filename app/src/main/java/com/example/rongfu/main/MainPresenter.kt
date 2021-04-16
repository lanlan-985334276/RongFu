package com.example.rongfu.main

class MainPresenter(private val pageView: MainContract.View) : MainContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
    }
}