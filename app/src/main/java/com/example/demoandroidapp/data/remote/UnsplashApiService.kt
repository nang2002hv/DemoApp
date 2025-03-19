package com.example.demoandroidapp.data.remote

import com.example.demoandroidapp.data.remote.response.CollectionItemResponse
import com.example.demoandroidapp.data.remote.response.SearchPhotoResult
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("/collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<CollectionItemResponse>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchPhotoResult

    companion object {
        operator fun invoke(retrofit: Retrofit): UnsplashApiService = retrofit.create()
    }
}