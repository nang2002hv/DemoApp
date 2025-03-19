package com.example.demoandroidapp.persentation.search.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoandroidapp.UnsplashServiceLocator
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.databinding.FragmentSearchPhotosBinding
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionItemAdapter
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState.CollectionItem

class SearchPhotosFragment : BaseFragmentWithViewBinding<FragmentSearchPhotosBinding>(
    inflateViewBinding = FragmentSearchPhotosBinding::inflate
) {

    private val feedCollectionItemAdapter by lazy (LazyThreadSafetyMode.NONE){
        FeedCollectionItemAdapter(
            onItemClicked = ::onItemClick,
            requestManager = Glide.with(this)
        )
    }

    private val viewmodel by activityViewModels<SearchViewModel>(
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

    private fun onItemClick(uiSate : CollectionItem){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpApdater()
        bindVH()
    }

    private fun setUpApdater() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            adapter = feedCollectionItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindVH() {
        viewmodel.searchResultResponse.observe(viewLifecycleOwner) {
            feedCollectionItemAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = SearchPhotosFragment()
    }

}
