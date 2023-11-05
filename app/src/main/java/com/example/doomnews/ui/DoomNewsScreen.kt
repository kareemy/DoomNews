package com.example.doomnews.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doomnews.R
import com.example.doomnews.data.DataSource
import com.example.doomnews.model.NewsArticle
import com.example.doomnews.ui.theme.DoomNewsTheme
import com.example.doomnews.ui.utils.DoomNewsContentType

@Composable
fun DoomNewsApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: DoomNewsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val contentType: DoomNewsContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = DoomNewsContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            contentType = DoomNewsContentType.FEED
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = DoomNewsContentType.FEED_LIST_DETAIL
        }
        else -> {
            contentType = DoomNewsContentType.LIST_ONLY
        }
    }

    Scaffold(
        topBar = {
            DoomNewsAppBar(
                onBackButtonClick = { viewModel.navigateToListPage() },
                isShowingListPage = uiState.isShowingListPage
            )
        }
    ) { innerPadding ->
        if (contentType == DoomNewsContentType.FEED_LIST_DETAIL) {
            if (uiState.isShowingListPage) {
                // FEED
                DoomNewsFeed(
                    articles = uiState.articlesList,
                    onClick = {
                        viewModel.updateCurrentArticle(it)
                        viewModel.navigateToDetailPage()
                    },
                    contentPadding = innerPadding
                )
            } else {
                // LIST AND DETAIL
                DoomNewsListAndDetails(
                    articles = uiState.articlesList,
                    onClick = {
                        viewModel.updateCurrentArticle(it)
                    },
                    onBackPressed = { viewModel.navigateToListPage() },
                    selectedArticle = uiState.currentArticle,
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    imageSize = R.dimen.image_size_large
                )
            }
        } else if (contentType == DoomNewsContentType.FEED) {
            if (uiState.isShowingListPage) {
                // FEED
                DoomNewsFeed(
                    articles = uiState.articlesList,
                    onClick = {
                        viewModel.updateCurrentArticle(it)
                        viewModel.navigateToDetailPage()
                    },
                    contentPadding = innerPadding
                )
            } else {
                DoomNewsDetail(
                    selectedArticle = uiState.currentArticle,
                    onBackPressed = { viewModel.navigateToListPage() },
                    contentPadding = innerPadding,
                    imageSize = R.dimen.image_size_large
                )
            }
        } else {
            if (uiState.isShowingListPage) {
                DoomNewsList(
                    articles = uiState.articlesList,
                    onClick = {
                        viewModel.updateCurrentArticle(it)
                        viewModel.navigateToDetailPage()
                    },
                    contentPadding = innerPadding,
                )
            } else {
                DoomNewsDetail(
                    selectedArticle = uiState.currentArticle,
                    onBackPressed = { viewModel.navigateToListPage() },
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoomNewsAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = if (!isShowingListPage) {
                    stringResource(R.string.app_name) + " - Article"
                } else {
                    stringResource(R.string.app_name)
                }
            )
        },
        navigationIcon = if (!isShowingListPage) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else {
            { Box {} }
        },
        //colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier,
    )
}

@Composable
fun DoomNewsFeed(
    articles: List<NewsArticle>,
    onClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding
    ) {
        items(articles) { article ->
            DoomNewsListItem(
                newsArticle = article,
                onItemClick = onClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
fun DoomNewsList(
    articles: List<NewsArticle>,
    onClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(articles) { article ->
            DoomNewsListItem(
                newsArticle = article,
                onItemClick = onClick,
                //modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoomNewsListItem(
    newsArticle: NewsArticle,
    onItemClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    // Card, Column, Image, Text, Text
    Card(
        modifier = modifier,
        onClick = { onItemClick(newsArticle) }
    ) {
        Column(

        ) {
            DoomNewsImage(imageResourceId = newsArticle.imageResourceId)
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(newsArticle.headlineResourceId),
                style = MaterialTheme.typography.titleLarge,
                minLines = 3,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
            Text(
                text = stringResource(id = R.string.author_line,
                    stringResource(newsArticle.authorResourceId), newsArticle.lastUpdateTime),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
        }
    }
}
@Composable
private fun DoomNewsDetail(
    selectedArticle: NewsArticle,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    @DimenRes imageSize: Int = R.dimen.image_size_small
) {
    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(contentPadding)
    ) {
        DoomNewsImage(imageResourceId = selectedArticle.imageResourceId, imageSize)
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(id = selectedArticle.headlineResourceId),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.author_line,
                stringResource(selectedArticle.authorResourceId), selectedArticle.lastUpdateTime),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(thickness = 1.dp, modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(id = selectedArticle.articleResourceId),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 40.dp
            )
        )
    }
}

@Composable
private fun DoomNewsImage(
    @DrawableRes imageResourceId: Int,
    @DimenRes imageSize: Int = R.dimen.image_size_small
) {
    Image(
        painter = painterResource(imageResourceId),
        contentDescription = null,
        modifier = Modifier
            .height(dimensionResource(imageSize))
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DoomNewsListAndDetails(
    articles: List<NewsArticle>,
    onClick: (NewsArticle) -> Unit,
    onBackPressed: () -> Unit,
    selectedArticle: NewsArticle,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    @DimenRes imageSize: Int = R.dimen.image_size_small
) {
    Row(modifier = modifier) {
        DoomNewsList(
            articles = articles,
            onClick = onClick,
            contentPadding = contentPadding,
            modifier = Modifier
                .weight(1f)
                .padding(
                    //top = 16.dp,
                    //start = 16.dp,
                    end = 16.dp
                )
        )
        DoomNewsDetail(
            selectedArticle = selectedArticle,
            onBackPressed = onBackPressed,
            contentPadding = contentPadding,
            modifier = Modifier.weight(2f),
            imageSize = imageSize
        )
    }
}

@Preview
@Composable
fun DoomNewsListPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsList(
                articles = DataSource.articles,
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
fun DoomNewsListItemPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsListItem(
                newsArticle = DataSource.articles[0],
                onItemClick = { }
            )
        }
    }
}

@Preview
@Composable
fun DoomNewsDetailPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsDetail(
                selectedArticle = DataSource.articles[0],
                onBackPressed = { }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun DoomNewsListAndDetailPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsListAndDetails(
                articles = DataSource.articles,
                onClick = { },
                onBackPressed =  { },
                selectedArticle = DataSource.articles[0],
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}