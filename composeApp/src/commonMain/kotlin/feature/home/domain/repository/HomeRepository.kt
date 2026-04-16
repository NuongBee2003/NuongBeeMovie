package feature.home.domain.repository

import core.network.ApiResult
import feature.home.domain.model.HomeMovie

interface HomeRepository {
    suspend fun getHomeMovies(): ApiResult<List<HomeMovie>>
}