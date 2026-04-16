package feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import core.ui.theme.AppColors
import feature.home.domain.model.HomeMovie

@Composable
fun MoviePosterCard(
    movie: HomeMovie,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(AppColors.SurfaceVariant)
        ) {
            AsyncImage(
                model = movie.thumbUrl,
                contentDescription = movie.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.6f to Color(0x33000000),
                            1f to Color(0xCC0B0616),
                        )
                    )
            )

            // Top tags
            val isTrailer = movie.episodeCurrent?.contains("trailer", ignoreCase = true) == true
            val topLeftTag = when {
                isTrailer -> "TRAILER"
                !movie.quality.isNullOrBlank() -> movie.quality
                else -> null
            }
            val secondTag = movie.lang?.takeIf { it.isNotBlank() }?.uppercase()

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (!topLeftTag.isNullOrBlank()) {
                    PosterTag(text = topLeftTag, bgColor = if (isTrailer) Color(0xFF2B66FF) else Color(0xFFE53935))
                }
                if (!secondTag.isNullOrBlank()) {
                    PosterTag(text = secondTag, bgColor = Color(0xFFFFC107), contentColor = Color(0xFF201A00))
                }
            }

            // Rating (ẩn nếu null)
            movie.rating?.let { rating ->
                if (rating > 0f) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xB01E1630))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "IMDB:",
                            color = AppColors.TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        val ratingText = ((rating * 10).toInt() / 10f).toString()
                        Text(
                            text = ratingText,
                            color = AppColors.TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier
                                .widthIn(min = 0.dp)
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = movie.name,
            color = AppColors.TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )

        val meta = buildString {
            movie.year?.let { append(it) }
            movie.quality?.takeIf { it.isNotBlank() }?.let {
                if (isNotEmpty()) append(" • ")
                append(it)
            }
        }
        if (meta.isNotBlank()) {
            Text(
                text = meta,
                color = AppColors.TextSecondary,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun PosterTag(
    text: String,
    bgColor: Color,
    contentColor: Color = Color.White,
) {
    Text(
        text = text,
        color = contentColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
