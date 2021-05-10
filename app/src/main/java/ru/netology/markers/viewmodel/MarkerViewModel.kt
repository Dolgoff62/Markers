package ru.netology.markers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.markers.db.AppDb
import ru.netology.markers.model.FeedModel
import ru.netology.markers.model.FeedModelState
import ru.netology.markers.repository.MarkerRepository
import ru.netology.markers.repository.MarkerRepositoryImpl
import ru.netology.markers.utils.SingleLiveEvent
import ru.netology.markers.utils.Utils.EmptyMarker.empty

class MarkerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarkerRepository =
        MarkerRepositoryImpl(AppDb.getInstance(context = application).markersDao())

    val data: LiveData<FeedModel> = repository.data.map(::FeedModel)
    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    val edited = MutableLiveData(empty)

    private val _markerCreated = SingleLiveEvent<Unit>()
    val markerCreated: LiveData<Unit>
        get() = _markerCreated

    fun saveMarker() {
        edited.value?.let {
            repository.saveMarker(it)
        }
        edited.value = empty
    }

    fun editMarker(description: String) {
        val text = description.trim()
        if (edited.value?.description == text) {
            return
        }
        edited.value = edited.value?.copy(description = text)
    }

    fun deleteMarker(id: Int) = repository.deleteMarker(id)
}