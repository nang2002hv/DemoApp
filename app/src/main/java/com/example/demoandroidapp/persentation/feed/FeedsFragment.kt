package com.example.demoandroidapp.persentation.feed


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.demoandroidapp.R
import androidx.fragment.app.add
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.databinding.FragmentFeedsBinding
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionsFragment
import com.example.demoandroidapp.persentation.feed.Photos.FeedPhotosFragment
import com.example.demoandroidapp.persentation.search.FragmentSearch
import com.google.android.material.tabs.TabLayoutMediator

class FeedsFragment : BaseFragmentWithViewBinding<FragmentFeedsBinding>(
  inflateViewBinding = FragmentFeedsBinding::inflate
) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    setUpViewPager()
    binding.searchButton.setOnClickListener {
      parentFragmentManager.commit {
        setReorderingAllowed(true)
        addToBackStack(null)
        add<FragmentSearch>(
          containerViewId = R.id.container_view,
          tag = FragmentSearch::class.java.name,
        )
      }

    }
  }

  private fun setUpViewPager(){
    binding.viewPager.run {
      adapter = FeedViewPagerAdapter(this@FeedsFragment)
    }
    TabLayoutMediator(binding.tabsLayout, binding.viewPager){ tab, position ->
      tab.text = when(position){
        0 -> "Collections"
        1 -> "Photos"
        else -> throw IllegalArgumentException("Invalid position")
      }
    }.attach()
  }

}


private class FeedViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
  override fun createFragment(position: Int) = when (position) {
    0 -> FeedCollectionsFragment.newInstance()
    1 -> FeedPhotosFragment.newInstance()
    else -> error("Invalid position: $position")
  }


  override fun getItemCount(): Int = 2

}