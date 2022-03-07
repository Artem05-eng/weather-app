package com.example.weatherdata.networking

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .url(
                originalRequest.url.newBuilder().addQueryParameter("key", Networking.apiKey).build()
            )
            .build()
        return chain.proceed(modifiedRequest)
    }
}