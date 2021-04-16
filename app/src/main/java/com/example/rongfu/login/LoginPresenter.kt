package com.example.rongfu.login

class LoginPresenter(private val pageView: LoginContract.View) :
    LoginContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
    }
}