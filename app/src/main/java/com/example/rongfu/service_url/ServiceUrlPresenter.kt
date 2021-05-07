package com.example.rongfu.service_url

class ServiceUrlPresenter(private val pageView: ServiceUrlContract.View) :
    ServiceUrlContract.Presenter {

    init {
        pageView.setPresenter(this)
    }

    override fun start() {
        pageView.initView()
        pageView.initLogic()
    }
}