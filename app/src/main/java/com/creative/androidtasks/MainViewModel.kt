package com.creative.androidtasks

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidtasks.repository.TaskRepo
import com.creative.androidtasks.ui.pagertab.state.TabUiState
import com.creative.androidtasks.ui.pagertab.state.TaskGroupUiState
import com.creative.androidtasks.ui.pagertab.state.TaskPageUiState
import com.creative.androidtasks.ui.pagertab.state.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

/**
 * Created by dan on 15/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepo: TaskRepo,
) : ViewModel(), TaskDelegate {
    private val _listTabGroup: MutableStateFlow<List<TaskGroupUiState>> = MutableStateFlow(emptyList())
    val listTabGroup = _listTabGroup.asStateFlow()

    init {
        _listTabGroup.value = listOf(
            TaskGroupUiState(
                tab = TabUiState(1, "Tab 1"),
                page = TaskPageUiState(
                    listOf(
                        TaskUiState(
                            id = 1,
                            content = "Task 1",
                            collectionId = 1
                        ),
                        TaskUiState(
                            id = 2,
                            content = "Task 2",
                            collectionId = 1
                        ),
                        TaskUiState(
                            id = 3,
                            content = "Task 3",
                            collectionId = 1,
                            isFavorite = true
                        )
                    ), listOf()
                )
            ),
            TaskGroupUiState(
                tab = TabUiState(2, "Tab 2"),
                page = TaskPageUiState(
                    listOf(), listOf()
                )
            ),
        )
    }

    override fun invertTaskFavorite(taskUiState: TaskUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTaskUiState = taskUiState.copy(isFavorite = !taskUiState.isFavorite)
            listTabGroup.value.let { listTabGroup ->
                val newTabGroup = listTabGroup.map { tabGroup ->
                    val newPage = tabGroup.page.copy(
                        activeTaskList = tabGroup.page.activeTaskList.map { task ->
                            if (task.id == newTaskUiState.id) newTaskUiState.copy() else task
                        },
                        completedTaskList = tabGroup.page.completedTaskList.map { task ->
                            if (task.id == newTaskUiState.id) newTaskUiState.copy() else task
                        }
                    )
                    tabGroup.copy(page = newPage)
                }
                _listTabGroup.value = newTabGroup
            }
        }
    }

    override fun invertTaskCompleted(taskUiState: TaskUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTaskUiState = taskUiState.copy(isCompleted = !taskUiState.isCompleted)
            listTabGroup.value.let { listTabGroup ->
                val newTabGroup = listTabGroup.map { tabGroup ->
                    val sumList = tabGroup.page.activeTaskList + tabGroup.page.completedTaskList
                    val updatedList = sumList.map { task ->
                        if (task.id == newTaskUiState.id) newTaskUiState.copy(updatedAt = Calendar.getInstance().timeInMillis) else task
                    }
                    val newPage = tabGroup.page.copy(
                        activeTaskList = updatedList.filter { !it.isCompleted }.sortedBy { it.updatedAt },
                        completedTaskList = updatedList.filter { it.isCompleted }.sortedBy { it.updatedAt }
                    )
                    tabGroup.copy(page = newPage)
                }
                _listTabGroup.value = newTabGroup
            }
        }
    }
}

interface TaskDelegate {
    fun invertTaskFavorite(taskUiState: TaskUiState) = Unit
    fun invertTaskCompleted(taskUiState: TaskUiState) = Unit
}