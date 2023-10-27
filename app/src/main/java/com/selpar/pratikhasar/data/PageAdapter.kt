package com.selpar.pratikhasar.data

import AracSahibiFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.selpar.pratikhasar.fragment.*

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 5;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return IstekSikayetFragment()
            }
            1 -> {
                return AracSahibiFragment()
            }
            2 -> {
                return SigortaFragment()
            }
            3 -> {
                return TumFragment()
            }
            4->{
                return OnarimFragment()
            }
            else -> {
                return IstekSikayetFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Tab 1"
            }
            1 -> {
                return "Tab 2"
            }
            2 -> {
                return "Tab 3"
            }
            3 -> {
                return "Tab 4"
            }
            4 -> {
                return "Tab 5   "
            }
        }
        return super.getPageTitle(position)
    }

}
