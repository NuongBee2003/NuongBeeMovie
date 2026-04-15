package navigation

import cafe.adriel.voyager.core.screen.Screen
import feature.category.ui.CategoryScreen
import feature.home.ui.HomeScreen
import feature.profile.ui.ProfileScreen
import feature.search.ui.SearchScreen

sealed class BottomNavItem(
    val title: String,
    val screen: Screen
) {
    data object Home : BottomNavItem("HOME", HomeScreen())
    data object Category : BottomNavItem("CATEGORY", CategoryScreen())
    data object Search : BottomNavItem("SEARCH", SearchScreen())
    data object Profile : BottomNavItem("PROFILE", ProfileScreen())
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Category,
    BottomNavItem.Search,
    BottomNavItem.Profile
)
