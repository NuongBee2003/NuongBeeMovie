package core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ui.theme.AppColors

@Composable
fun AppTopBar(
    title: String = "ETHER",
    onMenuClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = { DefaultAvatar() },
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(AppColors.Brand)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = { onMenuClick?.invoke() },
                enabled = onMenuClick != null,
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = AppColors.TextPrimary,
                )
            }

            Text(
                text = title,
                color = AppColors.TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = actions,
            )
        }
    }
}

@Composable
private fun DefaultAvatar() {
    Icon(
        imageVector = Icons.Filled.AccountCircle,
        contentDescription = "User Profile",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        tint = AppColors.TextPrimary
    )
}

@Preview
@Composable
fun AppTopBarPreview() {
    AppTopBar(
        title = "ETHER",
        onMenuClick = { /* TODO: open drawer */ },
        actions = {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                tint = AppColors.TextPrimary
            )
        }
    )
}
