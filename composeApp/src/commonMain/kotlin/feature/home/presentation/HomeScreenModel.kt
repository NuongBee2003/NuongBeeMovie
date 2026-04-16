package feature.home.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.network.ApiResult
import feature.home.data.HomeRepositoryImpl
import feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenModel(
    private val repository: HomeRepository = HomeRepositoryImpl(),
) : ScreenModel {
    private val _state = MutableStateFlow(HomeUiState(isLoading = true))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        getHomeMovies()
    }

    fun getHomeMovies() {
        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.getHomeMovies()) {
                is ApiResult.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        movies = result.data,
                    )
                }

                is ApiResult.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }
}
