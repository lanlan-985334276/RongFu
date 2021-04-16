
package com.example.rongfu.base.mvp

interface BaseView<T> {

    fun setPresenter(presenter: T)

    fun initView()

    fun initLogic()
}
