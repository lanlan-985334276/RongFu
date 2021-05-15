package com.example.rongfu.main.home.log

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class LogContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}