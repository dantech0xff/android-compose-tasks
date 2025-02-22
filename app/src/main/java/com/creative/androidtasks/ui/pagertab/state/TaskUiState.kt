package com.creative.androidtasks.ui.pagertab.state

data class TaskUiState(
    val id: Long,
    val content: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean,
    val collectionId: Long,
    val updatedAt: Long
)
