package com.example.rongfu.main.home

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class HomeContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}