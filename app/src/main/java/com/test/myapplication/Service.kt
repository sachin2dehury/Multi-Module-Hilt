package com.test.myapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("jokes/random")
    suspend fun getNetworkResponse(): Response<NetworkResponse>

    @GET("jokes/random")
    suspend fun getMoshiResponse(): Response<MoshiResponse>

    @GET("jokes/categories")
    suspend fun getJokeCategories(): Response<List<String?>?>
}
