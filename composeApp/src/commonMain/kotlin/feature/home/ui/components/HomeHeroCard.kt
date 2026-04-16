package feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale // Cần import thêm cái này
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import core.ui.theme.AppColors
import feature.home.domain.model.HomeMovie

@Composable
fun HomeHeroCard(
    movie: HomeMovie,
    modifier: Modifier = Modifier,
    onPlayClick: (() -> Unit)? = null,
    onAddClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(26.dp))
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
                        0f to Color(0x22000000),
                        0.45f to Color(0xAA0B0616),
                        1f to AppColors.Brand,
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "FEATURED MOVIE",
                color = AppColors.TextSecondary,
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color(0xFF221A36))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )

            Text(
                text = movie.name,
                color = AppColors.TextPrimary,
                fontSize = 42.sp,
                lineHeight = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            val meta = buildString {
                movie.episodeCurrent?.takeIf { it.isNotBlank() }?.let { append(it) }
                if (isNotEmpty()) append(" • ")
                movie.quality?.takeIf { it.isNotBlank() }?.let { append(it) }
                movie.lang?.takeIf { it.isNotBlank() }?.let {
                    if (isNotEmpty()) append(" • ")
                    append(it)
                }
                movie.year?.let {
                    if (isNotEmpty()) append(" • ")
                    append(it)
                }
            }

            if (meta.isNotBlank()) {
                Text(
                    text = meta,
                    color = AppColors.TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // SỬA Ở ĐÂY: Đổi Row thành Column để xếp dọc 2 nút
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // Nút "Xem Ngay" (Màu tím)
                Button(
                    onClick = { onPlayClick?.invoke() },
                    enabled = onPlayClick != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9462DF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Xem Ngay", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onAddClick?.invoke() },
                    enabled = onAddClick != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF221A36),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Danh Sách Của Tôi", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}