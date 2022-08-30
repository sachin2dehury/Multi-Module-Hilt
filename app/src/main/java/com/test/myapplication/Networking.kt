package com.test.myapplication

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.test.myapplication.EasyTrace.stopApiTrace
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {

    private val okHttp = OkHttpClient.Builder().addInterceptor {
        val request = it.request()
        if (Firebase.remoteConfig.getBoolean("test_app_api_load")) {
            val trace = EasyTrace.startApiTrace(request.url.encodedPath, request.method)
            val result = it.proceed(request)
            trace.stopApiTrace(result.code, result.body?.contentLength() ?: 0L)
            result
        } else it.proceed(request)
    }.build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    private val retrofit = Retrofit.Builder().baseUrl("https://api.chucknorris.io/")
        .client(okHttp)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val service = retrofit.create(Service::class.java)
}
