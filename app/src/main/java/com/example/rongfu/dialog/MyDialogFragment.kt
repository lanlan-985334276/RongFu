package com.example.rongfu.dialog

import android.content.ContentValues
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.rongfu.R

class MyDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, null)
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
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        if (window != null) {
            // 一定要设置Background，如果不设置，window属性设置无效
            window.setBackgroundDrawableResource(android.R.color.transparent)
            val params = window.attributes
            params.gravity = Gravity.CENTER
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = params
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
}