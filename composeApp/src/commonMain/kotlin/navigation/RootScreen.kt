package navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import core.ui.theme.AppColors

/** Root screen hosting bottom navigation for 4 pages */
class RootScreen : Screen {
    @Composable
    override fun Content() {
        Navigator(BottomNavItem.Home.screen) { navigator ->
            Scaffold(
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(AppColors.Brand)
                    ) {
                        NavigationBar(
                            containerColor = AppColors.SurfaceVariant,
                            tonalElevation = 0.dp
                        ) {
                            val current = navigator.lastItem
                            bottomNavItems.forEach { item ->
                                val selected = current::class == item.screen::class
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        if (!selected) navigator.replace(item.screen)
                                    },
                                    icon = {},
                                    label = { Text(item.title) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = AppColors.IconSelected,
                                        unselectedIconColor = AppColors.IconUnselected,
                                        selectedTextColor = AppColors.IconSelected,
                                        unselectedTextColor = AppColors.IconUnselected,
                                        indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    CurrentScreen()
                }
            }
        }
    }
}
