package core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = AppColors.Brand,
    onPrimary = AppColors.TextPrimary,
    primaryContainer = AppColors.SurfaceVariant,
    onPrimaryContainer = AppColors.TextPrimary,
    background = AppColors.Brand,
    onBackground = AppColors.TextPrimary,
    surface = AppColors.Surface,
    onSurface = AppColors.TextPrimary,
    surfaceVariant = AppColors.SurfaceVariant,
    onSurfaceVariant = AppColors.TextSecondary,
    outline = Color(0xFF3B2A55)
)

@Composable
fun NuongBeeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}

