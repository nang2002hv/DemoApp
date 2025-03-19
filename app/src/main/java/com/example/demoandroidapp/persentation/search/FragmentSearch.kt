package com.example.demoandroidapp.persentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.demoandroidapp.UnsplashServiceLocator
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.data.remote.UnsplashApiService
import com.example.demoandroidapp.databinding.FragmentSearchBinding
import com.example.demoandroidapp.persentation.search.photos.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FragmentSearch : BaseFragmentWithViewBinding<FragmentSearchBinding>(
  inflateViewBinding = FragmentSearchBinding::inflate
) {

    private val searchViewPagerAdapter by lazy(LazyThreadSafetyMode.NONE){
        SearchViewPagerAdapter(this)
    }

    private val viewModel by activityViewModels<SearchViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(SearchViewModel::class) {
                    SearchViewModel(
                        unsplashApiService = UnsplashServiceLocator.unsplashApiService
                    )
                }
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.run {
            adapter = SearchViewPagerAdapter(this@FragmentSearch)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Photos"
                1 -> "Users"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
        setUpAdapter()
    }

    fun setUpAdapter(){
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.searchEditText.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                viewModel.setQerry(s.toString())
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.setQerry(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.setQerry(s.toString())
            }

        })
    }

}
