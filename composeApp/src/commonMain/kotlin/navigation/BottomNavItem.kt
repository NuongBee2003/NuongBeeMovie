package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import feature.category.ui.CategoryScreen
import feature.home.ui.HomeScreen
import feature.profile.ui.ProfileScreen
import feature.search.ui.SearchScreen

sealed class BottomNavItem(
    val title: String,
    val screen: Screen,
    val icon: ImageVector
) {
    data object Home : BottomNavItem("HOME", HomeScreen(), Icons.Filled.Home)
    data object Category : BottomNavItem("CATEGORY", CategoryScreen(), Icons.Filled.Category)
    data object Search : BottomNavItem("SEARCH", SearchScreen(), Icons.Filled.Search)
    data object Profile : BottomNavItem("PROFILE", ProfileScreen(), Icons.Filled.AccountCircle)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Category,
    BottomNavItem.Search,
    BottomNavItem.Profile
)
