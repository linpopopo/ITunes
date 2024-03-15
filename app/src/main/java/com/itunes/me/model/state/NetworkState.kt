package com.itunes.me.model.state

import com.itunes.me.model.ItunesData

sealed class NetworkState {
    data class Success(val data: ItunesData) : NetworkState()
    object Loading : NetworkState()
    data class Error(val msg: String) : NetworkState()
}