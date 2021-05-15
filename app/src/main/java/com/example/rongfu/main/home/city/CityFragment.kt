package com.example.rongfu.main.home.city

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rongfu.R
import kotlinx.android.synthetic.main.fragment_city.*

class CityFragment(private val listener: OnClickListener) : DialogFragment() {
    private val citys = mutableListOf<String>()
    private val result = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCitys()
        initLogic()
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        if (window != null) {
            // 一定要设置Background，如果不设置，window属性设置无效
            window.setBackgroundDrawableResource(android.R.color.white)
            val params = window.attributes
            params.gravity = Gravity.CENTER
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = params
        }
    }

    private fun getCitys() {
        citys.clear()
        val before = context!!.getString(R.string.all_citys)
        val provinceArr = before.split(" ")
        Log.i("City", provinceArr.size.toString())
        for (s in provinceArr) {
            val cityArr = s.split("%")
            for (i in cityArr.indices) {
                if (i == 0) continue
                citys.add("${cityArr[i]}-${cityArr[0]}")
                Log.i("City", "${cityArr[i]}-${cityArr[0]}")
            }
        }
        Log.i("City",citys.size.toString())
    }

    private fun initLogic() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CityAdapter(context!!, citys)
        recyclerView.adapter = adapter
        adapter.setListener(object : CityAdapter.OnClickListener {
            override fun onClick() {
                listener.onClick()
                dismiss()
            }
        })
        et_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s ?: return
                if (s.length==0) return
                Log.i("City",s.toString())
                result.clear()
                for (c in citys) {
                    if (c.contains(s.toString())) result.add(c)
                }
                adapter.setData(result)
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })
    }

    fun show(manager: FragmentManager) {
        show(manager, ContentValues.TAG)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if (!isAdded) {
                val transaction = manager.beginTransaction()
                transaction.add(this, tag)
                transaction.commitAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        if (isAdded && !isDetached && dialog != null && dialog!!.isShowing) {
            super.dismissAllowingStateLoss()
        }
    }

    override fun dismissAllowingStateLoss() {
        if (isAdded && !isDetached && dialog != null && dialog!!.isShowing) {
            super.dismissAllowingStateLoss()
        }
    }

    interface OnClickListener {
        fun onClick()
    }
}