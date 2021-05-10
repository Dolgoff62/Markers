package ru.netology.markers.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.markers.dao.MarkersDao
import ru.netology.markers.dto.Marker
import ru.netology.markers.entity.MarkersEntity

class MarkerRepositoryImpl(private val dao: MarkersDao) : MarkerRepository {
    override val data: LiveData<List<Marker>> = dao.getAll().map {
        it.map(MarkersEntity::toDto)
    }

    override fun saveMarker(marker: Marker) {
        dao.insert(MarkersEntity.fromDto(marker))
    }

    override fun deleteMarker(id: Int) {
        dao.deleteById(id)
    }
}