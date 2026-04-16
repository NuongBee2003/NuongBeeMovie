package feature.home.data

import feature.home.data.dto.HomeResponseDto

interface OphimApi {
    suspend fun getHome(): HomeResponseDto

    suspend fun getMovieDetail(slug: String): feature.detail.data.dto.MovieDetailResponseDto
}

