package feature.home.presentation

import feature.home.domain.model.HomeMovie

data class HomeUiState(
    val title: String = "HOME",
    val subtitle: String = "NuongBee Movie",

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movies: List<HomeMovie> = emptyList(),
)
