package feature.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    val status: String? = null,
    val message: String? = null,
    val data: HomeDataDto? = null,
)

@Serializable
data class HomeDataDto(
    val seoOnPage: SeoOnPageDto? = null,
    val items: List<MovieItemDto> = emptyList(),
    val itemsSportsVideos: List<MovieItemDto> = emptyList(),
    val params: ParamsDto? = null,
    val type_list: String? = null,
    val APP_DOMAIN_FRONTEND: String? = null,
    val APP_DOMAIN_CDN_IMAGE: String? = null,
)

@Serializable
data class SeoOnPageDto(
    val titleHead: String? = null,
    val descriptionHead: String? = null,
    val og_type: String? = null,
    val og_image: List<String> = emptyList(),
)

@Serializable
data class MovieItemDto(
    val tmdb: TmdbDto? = null,
    val imdb: ImdbDto? = null,
    val modified: ModifiedDto? = null,

    @SerialName("_id")
    val id: String? = null,

    val name: String? = null,
    val slug: String? = null,
    val origin_name: String? = null,
    val alternative_names: List<String> = emptyList(),
    val type: String? = null,
    val thumb_url: String? = null,
    val sub_docquyen: Boolean? = null,
    val time: String? = null,
    val episode_current: String? = null,
    val quality: String? = null,
    val lang: String? = null,
    val year: Int? = null,
    val category: List<CategoryDto> = emptyList(),
    val country: List<CountryDto> = emptyList(),
)

@Serializable
data class TmdbDto(
    val type: String? = null,
    val id: String? = null,
    val season: Int? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
)

@Serializable
data class ImdbDto(
    val id: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
)

@Serializable
data class ModifiedDto(
    val time: String? = null,
)

@Serializable
data class CategoryDto(
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null,
)

@Serializable
data class CountryDto(
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null,
)

@Serializable
data class ParamsDto(
    val type_slug: String? = null,
    val filterCategory: List<String> = emptyList(),
    val filterCountry: List<String> = emptyList(),
    val filterYear: String? = null,
    val sortField: String? = null,
    val pagination: PaginationDto? = null,
    val itemsUpdateInDay: Int? = null,
    val totalSportsVideos: Int? = null,
    val itemsSportsVideosUpdateInDay: Int? = null,
)

@Serializable
data class PaginationDto(
    val totalItems: Int? = null,
    val totalItemsPerPage: Int? = null,
    val currentPage: Int? = null,
    val pageRanges: Int? = null,
)

