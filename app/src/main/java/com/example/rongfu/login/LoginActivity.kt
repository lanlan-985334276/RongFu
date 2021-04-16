package com.example.rongfu.login

import android.os.Bundle
import android.os.PersistableBundle
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseActivity

class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter
    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)
        LoginPresenter(this).start()
    }

    override fun initView() {

    }

    override fun initLogic() {
    }
}