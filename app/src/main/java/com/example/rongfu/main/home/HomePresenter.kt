package com.example.rongfu.main.home

class HomePresenter(private val pageView: HomeContract.View) : HomeContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}