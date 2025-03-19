package com.example.demoandroidapp.persentation.search.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.demoandroidapp.data.remote.UnsplashApiService
import com.example.demoandroidapp.persentation.debounce
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class SearchViewModel(
    private val unsplashApiService: UnsplashApiService
) : ViewModel() {
    private val _querySearchLiveData = MutableLiveData<String>()
    val querySearch get() = _querySearchLiveData

    val searchResultResponse = querySearch
        .debounce(650L, viewModelScope)
        .distinctUntilChanged()
        .switchMap { query ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                val resultSearch = unsplashApiService.searchPhotos(query, 1, 10)
                    .results
                    .map{coverPhoto ->
                        FeedCollectionUiState.CollectionItem(
                            id = coverPhoto.id,
                            imageUrl = coverPhoto.urls.regular,
                            title = coverPhoto.description ?: "",
                            description = coverPhoto.user.username
                        )
                    }
                emit(resultSearch)
            } catch (e : Exception){
                emit(emptyList())
            }
        }
    }

    fun setQerry(query: String){
        _querySearchLiveData.value = query
    }


}