package feature.search.presentation

import feature.search.domain.model.SearchMovie
import feature.search.domain.model.WatchedMovie

data class SearchUiState(
    val query: String = "",
    val title: String? = null,
    val items: List<SearchMovie> = emptyList(),

    // Tìm kiếm gần đây
    val recentQueries: List<String> = emptyList(),

    // Phim đã xem gần đây
    val recentlyWatched: List<WatchedMovie> = emptyList(),

    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = false,
    val page: Int = 1,
    val errorMessage: String? = null,
)
