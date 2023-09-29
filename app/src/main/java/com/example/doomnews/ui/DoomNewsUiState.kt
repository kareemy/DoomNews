package com.example.doomnews.ui

import com.example.doomnews.data.DataSource
import com.example.doomnews.model.NewsArticle

data class DoomNewsUiState(
    val articlesList: List<NewsArticle> = DataSource.articles,
    val currentArticle: NewsArticle = DataSource.articles[0],
    val isShowingListPage: Boolean = true
)
