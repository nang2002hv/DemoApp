package com.example.demoandroidapp

import androidx.annotation.MainThread
import com.example.demoandroidapp.data.remote.UnsplashApiService
import com.example.demoandroidapp.data.remote.interceptor.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashServiceLocator {
  const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

  // @MainThread
  private var _application: UnsplashApplication? = null

  @MainThread
  fun initWith(app: UnsplashApplication) {
    _application = app
  }

  @get:MainThread
  val application: UnsplashApplication
    get() = checkNotNull(_application) {
      "UnsplashServiceLocator must be initialized. " +
        "Call UnsplashServiceLocator.initWith(this) in your Application class."
    }

  // ------------------------------------------------------------

  private val moshi: Moshi by lazy {
    Moshi.Builder()
      .addLast(KotlinJsonAdapterFactory())
      .build()
  }

  private val httpLoggingInterceptor
    get() = HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }

  private val authorizationInterceptor: AuthorizationInterceptor
    get() = AuthorizationInterceptor(clientId = BuildConfig.UNSPLASH_CLIENT_ID)

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(UNSPLASH_BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .client(okHttpClient)
      .build()
  }

  val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .addNetworkInterceptor(httpLoggingInterceptor)
      .addInterceptor(authorizationInterceptor)
      .build()
  }

  val unsplashApiService: UnsplashApiService by lazy { UnsplashApiService(retrofit) }
}
