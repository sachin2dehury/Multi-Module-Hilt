package com.test.myapplication

import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("jokes/random")
    suspend fun getNetworkResponse(): Response<NetworkResponse>

    @GET("jokes/random")
    suspend fun getMoshiResponse(): Response<MoshiResponse>
}
