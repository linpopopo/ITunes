package com.itunes.me.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itunes.me.net.ApiFactory
import com.itunes.me.model.ItunesData
import com.itunes.me.model.state.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState = MutableSharedFlow<NetworkState>()
    val uiState get() = _uiState.asSharedFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiState.emit(NetworkState.Loading)
                ApiFactory.apiService.fetchITunesData(term = "歌", limit = 200, country = "HK")
            }.onSuccess {
                _uiState.emit(NetworkState.Success(it))
            }.onFailure {
                it.printStackTrace()
                _uiState.emit(NetworkState.Error("网络错误，请稍后重试"))
            }
        }
    }

}