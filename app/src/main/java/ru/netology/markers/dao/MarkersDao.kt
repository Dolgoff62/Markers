package ru.netology.markers.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.markers.dto.Marker
import ru.netology.markers.entity.MarkersEntity


@Dao
interface MarkersDao {

    @Query("SELECT * FROM MarkersEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<MarkersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marker: MarkersEntity)

    @Query("DELETE FROM MarkersEntity WHERE id = :id")
    fun deleteById(id: Int)
}
