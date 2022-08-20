package com.test.myapplication

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// @OptIn(ExperimentalSerializationApi::class)
val instance = Retrofit.Builder().baseUrl("https://api.chucknorris.io/")
//    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()
        )
    )
    .build().create(Service::class.java)
