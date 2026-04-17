package navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import core.ui.components.AppTopBar
import core.ui.nav.LocalNavVisibilityController
import core.ui.nav.NavVisibilityController
import core.ui.theme.AppColors

class RootScreen : Screen {
    @Composable
    override fun Content() {
        Navigator(BottomNavItem.Home.screen) { navigator ->
            var navVisible by remember { mutableStateOf(true) }

            val controller = remember {
                object : NavVisibilityController {
                    override fun show() { navVisible = true }
                    override fun hide() { navVisible = false }
                }
            }

            CompositionLocalProvider(LocalNavVisibilityController provides controller) {
                Scaffold(
                    topBar = {
                        AnimatedVisibility(
                            visible = navVisible,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 2 }),
                            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it / 2 }),
                        ) {
                            val title = "BEE MOVIE".uppercase()

                            AppTopBar(
                                title = title,
                                onMenuClick = { /* TODO: open drawer */ },
                            )
                        }
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = navVisible,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
                        ) {
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
                                            icon = {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = item.title
                                                )
                                            },
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
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CurrentScreen()
                    }
                }
            }
        }
    }
}
