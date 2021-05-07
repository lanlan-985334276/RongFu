package com.example.rongfu.register

class RegisterPresenter(private val pageView: RegisterContract.View) : RegisterContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}