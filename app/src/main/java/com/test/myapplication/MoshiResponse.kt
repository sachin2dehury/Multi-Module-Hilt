package com.test.myapplication


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoshiResponse(
    val categories: List<Any?>? = null,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "icon_url")
    val iconUrl: String? = null,
    val id: String? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    val url: String? = null,
    val value: String? = null
)