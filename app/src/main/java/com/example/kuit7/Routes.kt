package com.example.kuit7

import kotlinx.serialization.Serializable

@Serializable
object NewsHome

@Serializable
object ContactHome

@Serializable
data class NewsDetailRoute(
    val image: Int,
    val category: String,
    val title: String,
    val source: String,
    val sourceIcon: Int,
    val time: String
)

@Serializable
data class ContactDetailRoute(
    val image: Int,
    val name: String,
    val phone: String
)