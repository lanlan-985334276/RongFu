package com.example.rongfu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rongfu.R
import com.example.rongfu.base.page.BaseFragment

class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initLogic() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}