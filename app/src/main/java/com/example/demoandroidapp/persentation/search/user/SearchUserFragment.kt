package com.example.demoandroidapp.persentation.search.user

import android.os.Bundle
import android.view.View
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.databinding.FragmentSearchUserBinding

class SearchUserFragment : BaseFragmentWithViewBinding<FragmentSearchUserBinding>(
  inflateViewBinding = FragmentSearchUserBinding::inflate
) {
  companion object {
    fun newInstance() = SearchUserFragment()
  }
}
