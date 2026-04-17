package feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import core.ui.theme.AppColors
import feature.detail.ui.MovieDetailScreen
import feature.search.domain.model.SearchMovie
import feature.search.domain.model.WatchedMovie
import feature.search.presentation.SearchScreenModel

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = rememberScreenModel { SearchScreenModel() }
        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(AppColors.Brand, AppColors.Surface, AppColors.Brand)
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Search bar like screenshot
            OutlinedTextField(
                value = state.query,
                onValueChange = screenModel::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.SurfaceVariant),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = AppColors.TextSecondary,
                    )
                },
                placeholder = { Text("Tìm kiếm phim…") },
            )

            // Tìm kiếm gần đây (chips)
            if (state.recentQueries.isNotEmpty() && state.query.isBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Tìm kiếm gần đây",
                        color = AppColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "XÓA TẤT CẢ",
                        color = AppColors.TextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable { screenModel.clearRecentQueries() },
                    )
                }

                FlowChips(
                    chips = state.recentQueries,
                    onChipClick = screenModel::onRecentQueryClick,
                )
            }

            // Phim đã xem gần đây (scroll ngang)
            if (state.recentlyWatched.isNotEmpty() && state.query.isBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Đã xem gần đây",
                        color = AppColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "XÓA TẤT CẢ",
                        color = AppColors.TextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable { screenModel.clearRecentlyWatched() },
                    )
                }

                RecentlyWatchedRow(
                    items = state.recentlyWatched,
                    onItemClick = { navigator?.push(MovieDetailScreen(it.slug)) },
                )
            }

            state.errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                )
            }

            if (state.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            if (state.items.isEmpty() && !state.isLoading && state.query.isNotBlank()) {
                Text(
                    text = "Không có kết quả",
                    color = AppColors.TextSecondary,
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                itemsIndexed(state.items, key = { _, item -> item.id }) { index, item ->
                    SearchResultRow(
                        movie = item,
                        onClick = {
                            screenModel.onMovieWatched(
                                WatchedMovie(
                                    id = item.id,
                                    name = item.name,
                                    slug = item.slug,
                                    thumbUrl = item.thumbUrl,
                                )
                            )
                            navigator?.push(MovieDetailScreen(item.slug))
                        },
                    )

                    if (index == state.items.lastIndex - 4) {
                        screenModel.loadMore()
                    }
                }

                if (state.isLoadingMore) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentlyWatchedRow(
    items: List<WatchedMovie>,
    onItemClick: (WatchedMovie) -> Unit,
) {
    val scrollState = rememberScrollState()

    // poster size tuned so ~5 items fit on most phones
    val posterWidth = 66.dp
    val posterHeight = 92.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items.forEach { movie ->
            Column(
                modifier = Modifier
                    .width(posterWidth)
                    .clickable { onItemClick(movie) },
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(width = posterWidth, height = posterHeight)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColors.SurfaceVariant),
                ) {
                    AsyncImage(
                        model = movie.thumbUrl,
                        contentDescription = movie.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize(),
                    )
                }

                Text(
                    text = movie.name,
                    color = AppColors.TextPrimary,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun SearchResultRow(
    movie: SearchMovie,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.SurfaceVariant)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(width = 70.dp, height = 96.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.Surface),
        ) {
            AsyncImage(
                model = movie.thumbUrl,
                contentDescription = movie.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = movie.name,
                color = AppColors.TextPrimary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            movie.originName?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            val meta = listOfNotNull(
                movie.year?.toString(),
                movie.quality,
                movie.lang,
                movie.episodeCurrent,
            ).joinToString(" • ")

            if (meta.isNotBlank()) {
                Text(
                    text = meta,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
private fun FlowChips(
    chips: List<String>,
    onChipClick: (String) -> Unit,
) {
    // Simple flow layout without extra dependency
    val rows = mutableListOf<MutableList<String>>()
    var current = mutableListOf<String>()
    chips.forEach { c ->
        if (current.size >= 3) {
            rows += current
            current = mutableListOf()
        }
        current += c
    }
    if (current.isNotEmpty()) rows += current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { chip ->
                    Chip(text = chip, onClick = { onChipClick(chip) })
                }
            }
        }
    }
}

@Composable
private fun Chip(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(AppColors.SurfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = AppColors.TextPrimary,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
