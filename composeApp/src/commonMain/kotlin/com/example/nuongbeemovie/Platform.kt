package com.example.nuongbeemovie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform