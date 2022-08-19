package com.test.myapplication

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val instance = Retrofit.Builder().baseUrl("https://api.chucknorris.io/")
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .build().create(Service::class.java)
