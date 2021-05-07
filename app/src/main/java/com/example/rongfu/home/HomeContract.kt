package com.example.rongfu.home

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class HomeContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}