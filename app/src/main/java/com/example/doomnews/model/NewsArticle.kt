package com.example.doomnews.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NewsArticle(
    @StringRes val headlineResourceId: Int,
    @StringRes val articleResourceId: Int,
    @StringRes val authorResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val lastUpdateTime: String
)
