package com.selpar.pratikhasar.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val item:ArrayList<Fragment>,
                acivity:AppCompatActivity):FragmentStateAdapter(acivity) {
    override fun getItemCount(): Int {
        return item.size
    }

    override fun createFragment(position: Int): Fragment {
       return item[position]
    }
}