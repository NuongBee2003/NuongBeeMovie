package feature.search.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.network.ApiResult
import feature.search.data.SearchRepositoryImpl
import feature.search.domain.model.WatchedMovie
import feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenModel(
    private val repository: SearchRepository = SearchRepositoryImpl(),
) : ScreenModel {

    private val _state = MutableStateFlow(
        SearchUiState(
            recentQueries = listOf("Interstellar", "Dune: Part Two", "Cyberpunk 2077"),
            recentlyWatched = listOf(
                WatchedMovie(id = "demo-1", name = "Interstellar", slug = "interstellar", thumbUrl = null),
                WatchedMovie(id = "demo-2", name = "Dune: Part Two", slug = "dune-part-two", thumbUrl = null),
                WatchedMovie(id = "demo-3", name = "Cyberpunk 2077", slug = "cyberpunk-2077", thumbUrl = null),
            )
        )
    )
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query, errorMessage = null) }

        searchJob?.cancel()
        searchJob = screenModelScope.launch {
            delay(350)
            val q = query.trim()
            if (q.isBlank()) {
                _state.update {
                    it.copy(
                        title = null,
                        items = emptyList(),
                        isLoading = false,
                        isLoadingMore = false,
                        canLoadMore = false,
                        page = 1,
                        errorMessage = null,
                    )
                }
                return@launch
            }
            search(page = 1, append = false)
        }
    }

    fun onRecentQueryClick(query: String) {
        onQueryChange(query)
    }

    fun clearRecentQueries() {
        _state.update { it.copy(recentQueries = emptyList()) }
    }

    fun clearRecentlyWatched() {
        _state.update { it.copy(recentlyWatched = emptyList()) }
    }

    fun onMovieWatched(movie: WatchedMovie) {
        _state.update { s ->
            val updated = (listOf(movie) + s.recentlyWatched.filterNot { it.slug == movie.slug }).take(20)
            s.copy(recentlyWatched = updated)
        }
    }

    fun refresh() {
        val q = _state.value.query.trim()
        if (q.isBlank()) return
        searchJob?.cancel()
        searchJob = screenModelScope.launch { search(page = 1, append = false) }
    }

    fun loadMore() {
        val s = _state.value
        if (!s.canLoadMore || s.isLoading || s.isLoadingMore) return
        val nextPage = s.page + 1
        screenModelScope.launch { search(page = nextPage, append = true) }
    }

    private suspend fun search(page: Int, append: Boolean) {
        val q = _state.value.query.trim()
        if (q.isBlank()) return

        _state.update {
            if (append) it.copy(isLoadingMore = true, errorMessage = null)
            else it.copy(isLoading = true, errorMessage = null, items = emptyList(), page = 1)
        }

        when (val result = repository.search(keyword = q, page = page)) {
            is ApiResult.Success -> {
                val data = result.data
                val totalPages = data.totalPages
                val canLoadMore = if (totalPages != null) data.page < totalPages else data.items.isNotEmpty()

                _state.update { s ->
                    val merged = if (append) s.items + data.items else data.items

                    // update recent queries only on fresh searches (not load more)
                    val updatedRecent = if (!append) {
                        val cleaned = q.trim()
                        if (cleaned.isBlank()) s.recentQueries
                        else (listOf(cleaned) + s.recentQueries.filterNot { it.equals(cleaned, ignoreCase = true) })
                            .distinct()
                            .take(10)
                    } else s.recentQueries

                    s.copy(
                        title = data.title,
                        items = merged,
                        recentQueries = updatedRecent,
                        isLoading = false,
                        isLoadingMore = false,
                        canLoadMore = canLoadMore,
                        page = data.page,
                        errorMessage = null,
                    )
                }
            }

            is ApiResult.Error -> {
                _state.update { s ->
                    s.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        canLoadMore = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }
}
