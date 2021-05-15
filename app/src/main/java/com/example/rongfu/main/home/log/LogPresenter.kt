package com.example.rongfu.main.home.log

class LogPresenter(private val pageView: LogContract.View) : LogContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}