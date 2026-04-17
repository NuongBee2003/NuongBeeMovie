package feature.home.data

import feature.home.data.dto.HomeResponseDto
import feature.search.data.dto.SearchResponseDto

interface OphimApi {
    suspend fun getHome(): HomeResponseDto

    suspend fun getMovieDetail(slug: String): feature.detail.data.dto.MovieDetailResponseDto

    suspend fun search(keyword: String, page: Int = 1): SearchResponseDto
}
