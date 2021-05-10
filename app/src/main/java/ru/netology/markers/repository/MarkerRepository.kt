package ru.netology.markers.repository

import androidx.lifecycle.LiveData
import ru.netology.markers.dto.Marker

interface MarkerRepository {
    val data: LiveData<List<Marker>>
    fun saveMarker(marker: Marker)
    fun deleteMarker(id: Int)
}