package com.example.rongfu.user

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class UserContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}