package com.example.demoandroidapp.persentation.feed.Collection

sealed interface FeedCollectionUiState {
    data object Loading : FeedCollectionUiState
    data object Error : FeedCollectionUiState

    // page 1 -> N
    data class Content(
        val currentPage : Int,
        val data: List<CollectionItem>,
        val nextPage : NextPageState
    ) : FeedCollectionUiState

    data class CollectionItem(
        val id: String,
        val title: String,
        val description: String,
        val imageUrl: String,
    )

    enum class NextPageState {
        LOADING,
        ERROR,
        IDLE,
        NO_MORE
    }

    companion object

}