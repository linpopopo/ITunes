package com.itunes.me.model

import com.itunes.me.util.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ItunesData(
    val resultCount: Long = 0L,
    val results: List<Result> = listOf()
)

@Serializable
data class Result(
    val artistId: Long = 0L,
    val artistName: String = "",
    val artistViewUrl: String = "",
    val artworkUrl100: String = "",
    val artworkUrl30: String = "",
    val artworkUrl60: String = "",
    val artworkUrl600: String = "",
    val collectionArtistId: Long = 0L,
    val collectionArtistName: String = "",
    val collectionArtistViewUrl: String = "",
    val collectionCensoredName: String = "",
    val collectionExplicitness: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val collectionHdPrice: BigDecimal = BigDecimal.ZERO,
    val collectionId: Long = 0L,
    val collectionName: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val collectionPrice: BigDecimal = BigDecimal.ZERO,
    val collectionViewUrl: String = "",
    val contentAdvisoryRating: String = "",
    val country: String = "",
    val currency: String = "",
    val discCount: Long = 0L,
    val discNumber: Long = 0L,
    val feedUrl: String = "",
    val genreIds: List<String> = listOf(),
    val genres: List<String> = listOf(),
    val hasITunesExtras: Boolean = false,
    val isStreamable: Boolean = false,
    val kind: String = "",
    val longDescription: String = "",
    val previewUrl: String = "",
    val primaryGenreName: String = "",
    val releaseDate: String = "",
    val shortDescription: String = "",
    val trackCensoredName: String = "",
    val trackCount: Long = 0L,
    val trackExplicitness: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val trackHdPrice: BigDecimal = BigDecimal.ZERO,
    @Serializable(with = BigDecimalSerializer::class)
    val trackHdRentalPrice: BigDecimal = BigDecimal.ZERO,
    val trackId: Long = 0L,
    val trackName: String = "",
    val trackNumber: Long = 0L,
    @Serializable(with = BigDecimalSerializer::class)
    val trackPrice: BigDecimal = BigDecimal.ZERO,
    @Serializable(with = BigDecimalSerializer::class)
    val trackRentalPrice: BigDecimal = BigDecimal.ZERO,
    val trackTimeMillis: Long = 0L,
    val trackViewUrl: String = "",
    val wrapperType: String = ""
)