package com.test.myapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("https://api.chucknorris.io/jokes/random")
    suspend fun getNetworkResponse(): Response<NetworkResponse>

    @GET("https://api.chucknorris.io/jokes/random")
    suspend fun getMoshiResponse(): Response<MoshiResponse>

    @GET("https://api.chucknorris.io/jokes/categories")
    suspend fun getJokeCategories(): Response<List<String?>?>

    @GET("https://pokeapi.co/api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100000,
        @Query("offset") offset: Int = 0
    ): ResponseBody

    @GET("https://pokeapi.co/api/v2/pokemon/ditto")
    suspend fun getPokemon(): ResponseBody
}
