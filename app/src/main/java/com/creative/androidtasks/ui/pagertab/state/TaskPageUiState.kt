package com.creative.androidtasks.ui.pagertab.state

data class TaskPageUiState(
    val activeTaskList: List<TaskUiState>,
    val completedTaskList: List<TaskUiState>
)
