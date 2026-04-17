package com.example.nuongbeemovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

object AppContext {
    @Volatile
    private var context: android.content.Context? = null

    fun init(context: android.content.Context) {
        if (this.context == null) this.context = context.applicationContext
    }

    fun require(): android.content.Context =
        context ?: error("AppContext not initialized. Call AppContext.init() in MainActivity.onCreate().")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        AppContext.init(this)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}