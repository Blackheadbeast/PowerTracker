package com.zohaib.powertracker.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zohaib.powertracker.fragments.TimeUsageFragment

class ViewPagerTrackingDetailsAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 1//Change it later to support more tabs

    override fun createFragment(position: Int): Fragment {
        return TimeUsageFragment()
    }
}