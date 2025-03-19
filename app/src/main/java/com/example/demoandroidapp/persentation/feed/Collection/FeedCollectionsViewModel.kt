package com.rxmobileteam.com.android_007.lec9_unsplash_app.presentation.feed.collections

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoandroidapp.data.remote.UnsplashApiService
import com.example.demoandroidapp.data.remote.response.CollectionItemResponse
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

internal class FeedCollectionsViewModel(
    private val apiService: UnsplashApiService
) : ViewModel() {
    private var _uiState = MutableLiveData<FeedCollectionUiState>(Loading)
    val uiState get()= _uiState

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {

        viewModelScope.launch {
            _uiState.value = Loading
            try {
                val reponseItem = apiService.getCollections(page = 1, perPage = PER_PAGE)
                val collectionItems = reponseItem.map { it.toCollectionItem() }
                for(item in collectionItems){
                    Log.d("FeedCollectionsViewModel", "loadFirstPage: $item")
                }
                _uiState.value = Content(
                    currentPage = 1,
                    data = collectionItems,
                    nextPage = getNextPageState(pageSize = collectionItems.size)
                )
            }   catch (e : CancellationException){
                throw e
            }   catch (e : Throwable){
                _uiState.value = Error
            }
        }
    }

    fun loadNextPage() {
        val currentState  = _uiState.value!!
        when(currentState) {
            Loading,
            Error,
                -> return
            is Content -> {
                when(currentState.nextPage) {
                    NextPageState.NO_MORE -> return
                    NextPageState.LOADING -> return
                    NextPageState.ERROR -> return
                    NextPageState.IDLE -> {
                        loadNextPageInternal(currentState)
                    }

                }
            }
        }

    }

    fun loadNextPageInternal(currentState: Content){
        viewModelScope.launch {
            _uiState.value = currentState.copy(
                nextPage = NextPageState.LOADING
            )

            val nextPageState = currentState.currentPage + 1

            try {
                val reponseItem = apiService.getCollections(page = 1, perPage = PER_PAGE)
                val newPageCollectionItems = reponseItem.map { it.toCollectionItem() }
                for(item in newPageCollectionItems){
                    Log.d("FeedCollectionsViewModel", "loadFirstPage: $item")
                }
                _uiState.value = currentState.copy(
                    data = (currentState.data + newPageCollectionItems).distinctBy {
                        it.id
                    },
                    currentPage = nextPageState,
                    nextPage = getNextPageState(pageSize = newPageCollectionItems.size)
                )

            }   catch (e : CancellationException){
                throw e
            }   catch (e : Throwable){
                _uiState.value = currentState.copy(
                    nextPage = NextPageState.ERROR
                )
            }
        }
    }

    private companion object {
        const val PER_PAGE = 30
        private fun getNextPageState(pageSize : Int) : NextPageState {
            return if (pageSize < PER_PAGE) NextPageState.NO_MORE
            else NextPageState.IDLE
        }
    }

     fun CollectionItemResponse.toCollectionItem() : FeedCollectionUiState.CollectionItem {
        return FeedCollectionUiState.CollectionItem(
            id = id,
            title = title,
            description = description ?: "",
            imageUrl = coverPhoto.urls.regular
        )
    }



}

