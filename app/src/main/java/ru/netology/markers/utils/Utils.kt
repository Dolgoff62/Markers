package ru.netology.markers.utils

import ru.netology.markers.dto.Marker

class Utils {
    object EmptyMarker {
        val empty = Marker(
            id = 0,
            markerTitle = "",
            markerDescription = "",
            publishedDate = "",
            latitude = 0.0,
            longitude = 0.0
        )
    }
}