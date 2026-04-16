package feature.home.data

import core.network.HttpClientProvider
import feature.home.data.dto.HomeResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorOphimApi(
    private val baseUrl: String = "https://ophim1.com",
) : OphimApi {

    override suspend fun getHome(): HomeResponseDto {
        return HttpClientProvider.httpClient
            .get("$baseUrl/v1/api/home")
            .body()
    }

    override suspend fun getMovieDetail(slug: String): feature.detail.data.dto.MovieDetailResponseDto {
        return HttpClientProvider.httpClient
            .get("$baseUrl/v1/api/phim/$slug")
            .body()
    }
}

