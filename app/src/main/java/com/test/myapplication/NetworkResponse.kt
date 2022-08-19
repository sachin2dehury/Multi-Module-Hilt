package com.test.myapplication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse(
    val categories: List<String?>? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("icon_url")
    val iconUrl: String? = null,
    val id: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val url: String? = null,
    val value: String? = null
)
