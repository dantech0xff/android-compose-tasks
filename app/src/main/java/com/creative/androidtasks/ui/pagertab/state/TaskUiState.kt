package com.creative.androidtasks.ui.pagertab.state

import com.creative.androidtasks.database.entity.TaskEntity
import java.util.Calendar
import java.util.Date

data class TaskUiState(
    val id: Long,
    val content: String,
    val isFavorite: Boolean = false,
    val isCompleted: Boolean = false,
    val collectionId: Long,
    val updatedAt: String,
)


fun TaskEntity.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        content = this.content,
        isFavorite = this.isFavorite,
        isCompleted = this.isCompleted,
        collectionId = this.collectionId,
        updatedAt = Date(this.updatedAt).toString()
    )
}

fun TaskUiState.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        content = this.content,
        isFavorite = this.isFavorite,
        isCompleted = this.isCompleted,
        collectionId = this.collectionId,
        updatedAt = Calendar.getInstance().timeInMillis
    )
}