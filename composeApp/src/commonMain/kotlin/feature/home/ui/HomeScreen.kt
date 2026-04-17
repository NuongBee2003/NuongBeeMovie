package feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import feature.home.ui.components.HomeHeroCard
import feature.home.ui.components.MoviePosterCard

class HomeScreen : Screen {
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
                    val hero = state.movies.firstOrNull()
                    val previewMovies = state.movies.drop(1).take(10) // 10 items = 5 dòng (2 cột)

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 0.dp,
                            bottom = 110.dp,
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (hero != null) {
                            item(key = "hero") {
                                // Full-width look: remove side padding just for hero
                                Box(modifier = Modifier.padding(horizontal = 0.dp)) {
                                    HomeHeroCard(
                                        movie = hero,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                }
                            }
                        }

                        // Header like screenshot + action "Xem Tất Cả"
                        item(key = "section-title") {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column {
                                    Text(
                                        text = "Phim Mới",
                                        color = AppColors.TextPrimary,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = "Cập nhật những siêu phẩm mới\nnhất tuần này",
                                        color = AppColors.TextSecondary,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .clickable { navigator?.push(AllMoviesScreen()) },
                                    text = "Xem\nTất Cả  >",
                                    color = AppColors.TextSecondary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }

                        // 2-column preview list (10 items)
                        items(
                            items = previewMovies.chunked(2),
                            key = { pair -> pair.joinToString("-") { it.id } },
                        ) { pair ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                            ) {
                                MoviePosterCard(
                                    movie = pair[0],
                                    modifier = Modifier.weight(1f),
                                    onClick = { navigator?.push(feature.detail.ui.MovieDetailScreen(pair[0].slug)) }
                                )
                                if (pair.size > 1) {
                                    MoviePosterCard(
                                        movie = pair[1],
                                        modifier = Modifier.weight(1f),
                                        onClick = { navigator?.push(feature.detail.ui.MovieDetailScreen(pair[1].slug)) }
                                    )
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


