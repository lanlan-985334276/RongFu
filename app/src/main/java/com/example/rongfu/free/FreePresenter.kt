package com.example.rongfu.free

class FreePresenter(private val pageView: FreeContract.View) : FreeContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}