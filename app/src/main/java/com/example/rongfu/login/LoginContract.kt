package com.example.rongfu.login

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class LoginContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}