package ru.netology.markers.model

import ru.netology.markers.dto.Marker

data class FeedModel(
    val markers: List<Marker> = emptyList(),
    val empty: Boolean = false
)
