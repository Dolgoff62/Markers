package ru.netology.markers.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.markers.dto.Marker


@Entity
data class MarkersEntity(
    @PrimaryKey(autoGenerate = true)
    val markerId: Int,
    val markerTitle: String,
    val markerDescription: String,
    val publishedDate: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDto() = Marker(
        markerId,
        markerTitle,
        markerDescription,
        publishedDate,
        latitude,
        longitude
    )


    companion object {
        fun fromDto(dto: Marker) =
            MarkersEntity(
                dto.markerId,
                dto.markerTitle,
                dto.markerDescription,
                dto.publishedDate,
                dto.latitude,
                dto.longitude
            )
    }
}

fun List<MarkersEntity>.toDto(): List<Marker> = map(MarkersEntity::toDto)
fun List<Marker>.toEntity(): List<MarkersEntity> = map(MarkersEntity::fromDto)
