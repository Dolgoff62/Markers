package ru.netology.markers.dto

data class Marker(
    val markerId: Int,
    val markerTitle: String,
    val markerDescription: String,
    val publishedDate: String,
    val latitude: Double,
    val longitude: Double
)
