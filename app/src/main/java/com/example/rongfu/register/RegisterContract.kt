package com.example.rongfu.register

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class RegisterContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}