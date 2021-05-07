package com.example.rongfu.service_url

import com.example.rongfu.base.mvp.BasePresenter
import com.example.rongfu.base.mvp.BaseView

class ServiceUrlContract {
    interface Presenter : BasePresenter
    interface View : BaseView<Presenter>
}