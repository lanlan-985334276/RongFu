package com.example.rongfu.main.user

class UserPresenter(private val pageView: UserContract.View) : UserContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}