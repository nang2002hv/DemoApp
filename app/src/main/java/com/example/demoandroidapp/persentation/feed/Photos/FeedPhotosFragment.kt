package com.example.demoandroidapp.persentation.feed.Photos

import android.os.Bundle
import android.view.View
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.databinding.FragmentFeedPhotosBinding

class FeedPhotosFragment : BaseFragmentWithViewBinding<FragmentFeedPhotosBinding>(
  inflateViewBinding = FragmentFeedPhotosBinding::inflate
) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // TODO: setup views
  }

  companion object {
    fun newInstance() = FeedPhotosFragment()
  }
}
