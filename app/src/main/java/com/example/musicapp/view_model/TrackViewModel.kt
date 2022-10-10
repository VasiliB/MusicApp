package com.example.musicapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.model.Track
import com.example.musicapp.model.repository.TrackRepository
import com.example.musicapp.utils.NetworkHelper
import com.example.musicapp.utils.Resource
import kotlinx.coroutines.launch


class TrackViewModel(
    private val trackRepository: TrackRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _tracks = MutableLiveData<Resource<List<Track>>>()
    val tracks: LiveData<Resource<List<Track>>>
        get() = _tracks

    init {
        fetchTracks()
    }

    private fun fetchTracks() {
        viewModelScope.launch {
            _tracks.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                trackRepository.getTracks().let {
                    if (it.isSuccessful) {
                        _tracks.postValue(Resource.success(it.body()))
                    } else _tracks.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _tracks.postValue(Resource.error("No internet connection", null))
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