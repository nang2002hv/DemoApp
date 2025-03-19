package com.example.demoandroidapp.persentation.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demoandroidapp.persentation.search.photos.SearchPhotosFragment
import com.example.demoandroidapp.persentation.search.user.SearchUserFragment

class SearchViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotosFragment.newInstance()
            1 -> SearchUserFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getItemCount() = COUNT_MAX

    companion object {
        const val COUNT_MAX = 2
    }
}