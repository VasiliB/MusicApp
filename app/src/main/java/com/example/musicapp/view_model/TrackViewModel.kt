package com.example.musicapp.view_model

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.model.entity.Track
import com.example.musicapp.model.repository.TrackRepository
import com.example.musicapp.utils.AppResult
import com.example.musicapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class TrackViewModel(private val trackRepository: TrackRepository) : ViewModel() {

    val showLoading = ObservableBoolean()
    val tracksList = MutableLiveData<List<Track>?>()
    val showError = SingleLiveEvent<String?>()

    fun getAllTracks() {
        showLoading.set(true)
        viewModelScope.launch {
            val result =  trackRepository.getAllTracks()

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    tracksList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }
}

//class TrackViewModel(private val trackRepository: TrackRepository) : ViewModel() {
//
//    private val _loadingState = MutableLiveData<LoadingState>()
//    val loadingState: LiveData<LoadingState>
//        get() = _loadingState
//
//    val data = trackRepository.data
//
//    init {
//        fetchData()
//    }
//
//    private fun fetchData() {
//        viewModelScope.launch {
//            try {
//                _loadingState.value = LoadingState.LOADING
//                trackRepository.refresh()
//                _loadingState.value = LoadingState.LOADED
//            } catch (e: Exception) {
//                _loadingState.value = LoadingState.error(e.message)
//            }
//        }
//    }
//}