package com.example.doomnews.ui

import androidx.lifecycle.ViewModel
import com.example.doomnews.model.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DoomNewsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DoomNewsUiState())
    val uiState: StateFlow<DoomNewsUiState> = _uiState.asStateFlow()

    fun updateCurrentArticle(selectedArticle: NewsArticle) {
        _uiState.update {
            it.copy(currentArticle = selectedArticle)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }
}