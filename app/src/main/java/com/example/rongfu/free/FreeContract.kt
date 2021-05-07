package com.example.rongfu.free

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class FreeContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}