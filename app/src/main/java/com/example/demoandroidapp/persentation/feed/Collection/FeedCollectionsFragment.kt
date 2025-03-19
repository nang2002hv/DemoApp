package com.example.demoandroidapp.persentation.feed.Collection

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoandroidapp.UnsplashServiceLocator
import com.example.demoandroidapp.core.BaseFragmentWithViewBinding
import com.example.demoandroidapp.databinding.FragmentFeedCollectionsBinding
import com.rxmobileteam.com.android_007.lec9_unsplash_app.presentation.feed.collections.FeedCollectionsViewModel

class FeedCollectionsFragment : BaseFragmentWithViewBinding<FragmentFeedCollectionsBinding>(
  inflateViewBinding = FragmentFeedCollectionsBinding::inflate
) {
  private val viewModel by viewModels<FeedCollectionsViewModel>(
    factoryProducer = {
        viewModelFactory {
          addInitializer(FeedCollectionsViewModel::class) {
            FeedCollectionsViewModel(
              apiService = UnsplashServiceLocator.unsplashApiService
            )
          }
        }
    }
  )

  private val feedCollectionItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
    FeedCollectionItemAdapter (
        onItemClicked = ::onItemClicked,
        requestManager = Glide.with(this@FeedCollectionsFragment)
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpView()
    bindViewModel()
  }

  private fun onItemClicked(collectionItem : FeedCollectionUiState.CollectionItem){
  }

  private fun render(uiState : FeedCollectionUiState){
    when(uiState){
      is FeedCollectionUiState.Loading -> {
        binding.run {
            progressBar.isVisible = true
            textError.isGone = true
        }
        Log.d("FeedCollectionsFragment", "render: loading")
        feedCollectionItemAdapter.submitList(emptyList())
      }

      is FeedCollectionUiState.Error -> {
        binding.run {
            progressBar.isGone = true
            textError.isVisible = true
            textError.text = "erro"
        }
        Log.d("FeedCollectionsFragment", "render: error")
        feedCollectionItemAdapter.submitList(emptyList())
      }

      is FeedCollectionUiState.Content -> {
        binding.run {
            progressBar.isGone = true
            textError.isGone = true
        }
        Log.d("FeedCollectionsFragment", "render: loading")
        Log.d("FeedCollectionsFragment", "render: ${uiState.data}")
        feedCollectionItemAdapter.submitList(uiState.data)
      }
    }
  }

  private fun setUpView(){
    binding.recyclerView.run {
      setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        adapter = feedCollectionItemAdapter
    }
  }

  private fun bindViewModel(){
    viewModel.uiState.observe(viewLifecycleOwner) {uiSate -> render(uiSate)}

    val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

    binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        println(">>> onScrolled: $dy")
        if(dy > 0){
          if(linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount){
            println(">>> onScrolled[*]")
            viewModel.loadNextPage()
          }
        }
      }
    })

  }

  override fun onDestroyView() {
    super.onDestroyView()
    Log.d("FragmentB", "onDestroyView() gọi, nhưng Fragment B vẫn còn tồn tại")
    binding.recyclerView.adapter = null
  }

  companion object {
    fun newInstance() = FeedCollectionsFragment()
    private const val VISIBLE_THRESHOLD = 3
  }


  override fun onDestroy() {
    super.onDestroy()
    Log.d("FragmentB", "onDestroy() gọi, Fragment B đã bị hủy hoàn toàn")
  }
}
