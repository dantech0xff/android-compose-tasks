package com.creative.androidtasks.ui.pagertab.state

import com.creative.androidtasks.database.entity.TaskEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TaskUiState(
    val id: Long,
    val content: String,
    val isFavorite: Boolean = false,
    val isCompleted: Boolean = false,
    val collectionId: Long,
    val updatedAt: Long,
    val stringUpdatedAt: String
)

fun TaskEntity.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        content = this.content,
        isFavorite = this.isFavorite,
        isCompleted = this.isCompleted,
        collectionId = this.collectionId,
        updatedAt = this.updatedAt,
        stringUpdatedAt = this.updatedAt.millisToDateString()
    )
}

fun Long.millisToDateString(): String {
    return SimpleDateFormat("EEE,dd MMM yyyy", Locale.getDefault()).format(Date(this)).toString()
}