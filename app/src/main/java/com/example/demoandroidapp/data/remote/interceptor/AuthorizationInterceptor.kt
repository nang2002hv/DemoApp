package com.example.demoandroidapp.data.remote.interceptor

import okhttp3.Interceptor

class AuthorizationInterceptor(
    private val clientId: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $clientId")
            .build()
            .let(chain::proceed)
}
