package feature.home.data

import core.network.ApiResult
import core.network.safeApiCall
import feature.home.domain.repository.HomeRepository
import feature.home.domain.model.HomeMovie

class HomeRepositoryImpl(
    private val api: OphimApi = KtorOphimApi(),
) : HomeRepository {

    override suspend fun getHomeMovies(): ApiResult<List<HomeMovie>> {
        return safeApiCall {
            val response = api.getHome()
            val data = response.data

            val cdn = data?.APP_DOMAIN_CDN_IMAGE
            val baseImageUrl = if (!cdn.isNullOrBlank()) cdn.trimEnd('/') else null

            data?.items.orEmpty().mapNotNull { item ->
                val id = item.id ?: return@mapNotNull null
                val name = item.name ?: return@mapNotNull null
                val slug = item.slug ?: return@mapNotNull null

                val thumb = item.thumb_url
                val fullThumbUrl = when {
                    thumb.isNullOrBlank() -> null
                    thumb.startsWith("http") -> thumb
                    baseImageUrl != null -> "$baseImageUrl/uploads/movies/${thumb.trimStart('/')}"
                    else -> thumb
                }

                val imdbRating = item.imdb?.vote_average?.toFloat()
                val tmdbRating = item.tmdb?.vote_average?.toFloat()
                val rating = imdbRating?.takeIf { it > 0f } ?: tmdbRating?.takeIf { it > 0f }

                HomeMovie(
                    id = id,
                    name = name,
                    slug = slug,
                    thumbUrl = fullThumbUrl,
                    year = item.year,
                    quality = item.quality,
                    lang = item.lang,
                    episodeCurrent = item.episode_current,
                    rating = rating,
                )
            }
        }
    }
}

