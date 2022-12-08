package com.test.myapplication

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.test.myapplication.EasyTrace.stopApiTrace
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {

    private val okHttp = OkHttpClient.Builder().build()

    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(Data::class.java, "id")
                .withSubtype(DataString::class.java, "string")
                .withSubtype(DataInt::class.java, "int")
        )
        .add(KotlinJsonAdapterFactory()).build()

    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    private val retrofit = Retrofit.Builder().baseUrl("https://api.chucknorris.io/")
        .client(okHttp)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val service = retrofit.create(Service::class.java)
}
