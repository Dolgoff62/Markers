package ru.netology.markers.dto

data class Marker(
    val id: Int,
    val markerTitle: String,
    val markerDescription: String,
    val publishedDate: String,
    val latitude: Double,
    val longitude: Double
)
