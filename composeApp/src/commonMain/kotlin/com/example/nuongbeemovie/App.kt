package com.example.nuongbeemovie

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import core.ui.AppSurface
import core.ui.theme.NuongBeeTheme
import navigation.RootScreen

@Composable
@Preview
fun App() {
    NuongBeeTheme {
        AppSurface {
            Navigator(RootScreen())
        }
    }
}