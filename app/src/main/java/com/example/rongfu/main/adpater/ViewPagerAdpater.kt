package com.example.rongfu.main.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rongfu.base.page.BaseFragment

class ViewPagerAdpater(
    private val fm: FragmentManager,
    private val list: List<BaseFragment>
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}