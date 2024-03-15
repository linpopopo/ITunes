package com.itunes.me.net

import com.itunes.me.model.ItunesData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("search")
    suspend fun fetchITunesData(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("country") country: String
    ): ItunesData

}