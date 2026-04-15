package feature.category.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryScreenModel : ScreenModel {
    private val _state = MutableStateFlow(CategoryUiState())
    val state: StateFlow<CategoryUiState> = _state.asStateFlow()
}

