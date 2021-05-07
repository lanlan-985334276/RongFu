package com.example.rongfu.utils

import android.widget.Toast

object ToastUtils {
    public fun showShort(text: String) {
        Toast.makeText(ContextHolder.getContext(), text, Toast.LENGTH_SHORT).show()
    }

    public fun showShort(text: Int) {
        showShort(ContextHolder.getContext().getString(text))
    }
}