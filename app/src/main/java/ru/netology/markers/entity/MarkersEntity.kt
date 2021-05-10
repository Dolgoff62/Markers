package ru.netology.markers.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.markers.dto.Marker


@Entity
data class MarkersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val description: String,
    val publishedDate: String
) {
    fun toDto() = Marker(
        id,
        description,
        publishedDate
    )


    companion object {
        fun fromDto(dto: Marker) =
            MarkersEntity(
                dto.id,
                dto.description,
                dto.publishedDate
            )
    }
}

fun List<MarkersEntity>.toDto(): List<Marker> = map(MarkersEntity::toDto)
fun List<Marker>.toEntity(): List<MarkersEntity> = map(MarkersEntity::fromDto)
