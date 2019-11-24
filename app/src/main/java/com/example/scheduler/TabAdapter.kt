package com.example.scheduler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter : FragmentPagerAdapter {

    private lateinit var title: List<String>

    constructor(fm: FragmentManager, behavior: Int, title: List<String>) : this(fm, behavior) {
        this.title = title
    }

    constructor(fm: FragmentManager, behavior: Int) : super(fm, behavior)

    override fun getItem(position: Int): Fragment {
        return TabFragment.newInstance(title[position])
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}