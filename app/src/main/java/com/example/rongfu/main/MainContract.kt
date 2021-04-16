package com.example.rongfu.main

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class MainContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}