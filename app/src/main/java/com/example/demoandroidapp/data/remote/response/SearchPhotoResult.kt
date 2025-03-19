package com.example.demoandroidapp.data.remote.response

import com.squareup.moshi.Json

data class SearchPhotoResult(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "results")
    val results: List<CollectionItemResponse.CoverPhoto>)