package com.test.myapplication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DummyResponse(
    val data: List<Data?>? = null
)

@JsonClass(generateAdapter = true)
open class Data(
    open val id: String? = null
)

@JsonClass(generateAdapter = true)
data class DataInt(
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "icon_url")
    val iconUrl: String? = null,
    override val id: String? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    val url: String? = null,
    val value: Int? = null
) : Data(id = id)

@JsonClass(generateAdapter = true)
data class DataString(
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "icon_url")
    val iconUrl: String? = null,
    override val id: String? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    val url: String? = null,
    val value: String? = null
) : Data(id = id)
