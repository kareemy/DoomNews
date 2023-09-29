package com.example.doomnews.data

import com.example.doomnews.R
import com.example.doomnews.model.NewsArticle

object DataSource {
    val articles = listOf(
        NewsArticle(
            headlineResourceId = R.string.headline_one,
            articleResourceId = R.string.article_one,
            authorResourceId = R.string.author_one,
            imageResourceId = R.drawable.alien,
            lastUpdateTime = "1 min ago"
        ),
        NewsArticle(
            headlineResourceId = R.string.headline_two,
            articleResourceId = R.string.article_two,
            authorResourceId = R.string.author_two,
            imageResourceId = R.drawable.antarctica,
            lastUpdateTime = "2 hours ago"
        ),
        NewsArticle(
            headlineResourceId = R.string.headline_three,
            articleResourceId = R.string.article_three,
            authorResourceId = R.string.author_three,
            imageResourceId = R.drawable.ai,
            lastUpdateTime = "3 d ago"
        ),
        NewsArticle(
            headlineResourceId = R.string.headline_four,
            articleResourceId = R.string.article_four,
            authorResourceId = R.string.author_four,
            imageResourceId = R.drawable.cats,
            lastUpdateTime = "5 mins ago"
        ),
        NewsArticle(
            headlineResourceId = R.string.headline_five,
            articleResourceId = R.string.article_five,
            authorResourceId = R.string.author_five,
            imageResourceId = R.drawable.timetravel,
            lastUpdateTime = "1 year from now"
        ),
        NewsArticle(
            headlineResourceId = R.string.headline_six,
            articleResourceId = R.string.article_six,
            authorResourceId = R.string.author_six,
            imageResourceId = R.drawable.cyber,
            lastUpdateTime = "42 mins ago"
        ),
    )
}