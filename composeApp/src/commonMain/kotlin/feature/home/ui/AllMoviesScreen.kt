package feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.ui.theme.AppColors
import feature.home.presentation.HomeScreenModel
import feature.home.ui.components.MoviePosterCard

/**
 * Screen hiển thị toàn bộ danh sách phim (tạm thời reuse HomeScreenModel).
 * Chưa làm view detail.
 */
class AllMoviesScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(AppColors.Brand, AppColors.Surface, AppColors.Brand)
                    )
                )
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.TextPrimary,
                    )
                }

                state.errorMessage != null -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        text = state.errorMessage ?: "Error",
                        color = AppColors.TextPrimary,
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 110.dp,
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item(key = "title") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = AppColors.TextPrimary,
                                    modifier = Modifier
                                        .clickable { navigator?.pop() }
                                        .padding(6.dp)
                                )
                                Text(
                                    text = "Xem Tất Cả",
                                    color = AppColors.TextPrimary,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }

                        items(
                            items = state.movies.chunked(2),
                            key = { pair -> pair.joinToString("-") { it.id } },
                        ) { pair ->
                            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                MoviePosterCard(movie = pair[0], modifier = Modifier.weight(1f))
                                if (pair.size > 1) {
                                    MoviePosterCard(movie = pair[1], modifier = Modifier.weight(1f))
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}








