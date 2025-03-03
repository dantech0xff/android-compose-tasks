package com.creative.androidtasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidtasks.repository.TaskRepo
import com.creative.androidtasks.ui.pagertab.state.TabUiState
import com.creative.androidtasks.ui.pagertab.state.TaskGroupUiState
import com.creative.androidtasks.ui.pagertab.state.TaskPageUiState
import com.creative.androidtasks.ui.pagertab.state.TaskUiState
import com.creative.androidtasks.ui.pagertab.state.millisToDateString
import com.creative.androidtasks.ui.pagertab.state.toTabUiState
import com.creative.androidtasks.ui.pagertab.state.toTaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

/**
 * Created by dan on 15/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

const val ID_ADD_NEW_LIST = -999L

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepo: TaskRepo,
) : ViewModel(), TaskDelegate {

    private val _eventFlow: MutableSharedFlow<MainEvent> = MutableSharedFlow()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _listTabGroup: MutableStateFlow<List<TaskGroupUiState>> = MutableStateFlow(emptyList())
    val listTabGroup = _listTabGroup.map {
        it + TaskGroupUiState(
            tab = TabUiState(ID_ADD_NEW_LIST, "Add New List"),
            page = TaskPageUiState(emptyList(), emptyList())
        )
    }

    private var _currentSelectedCollectionIndex: Int = 0
    init {
        viewModelScope.launch {
            val listTasksCollections = taskRepo.getTaskCollections()
            val listTabGroupUiState = listTasksCollections.ifEmpty {
                taskRepo.addTaskCollection("My Tasks")?.let { collection ->
                    val id = collection.id
                    taskRepo.addTask("Task 1", id)
                    taskRepo.addTask("Task 2", id)
                    taskRepo.addTask("Task 3", id)
                    taskRepo.addTask("Task 4", id)
                    taskRepo.addTask("Task 5", id)
                }
                taskRepo.getTaskCollections()
            }.map { taskCollection ->
                val collectionId = taskCollection.id
                val listTaskUiState = taskRepo.getTasksByCollectionId(collectionId).map { taskEntity ->
                    taskEntity.toTaskUiState()
                }
                val tabUiState = taskCollection.toTabUiState()
                TaskGroupUiState(tabUiState, TaskPageUiState(
                    activeTaskList = listTaskUiState.filter { !it.isCompleted }.sortedByDescending { it.updatedAt },
                    completedTaskList = listTaskUiState.filter { it.isCompleted }.sortedByDescending { it.updatedAt }
                ))
            }
            _listTabGroup.value = listTabGroupUiState
        }
    }

    override fun invertTaskFavorite(taskUiState: TaskUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTaskUiState = taskUiState.copy(isFavorite = !taskUiState.isFavorite)
            if (!taskRepo.updateTaskFavorite(newTaskUiState.id, newTaskUiState.isFavorite)) {
                return@launch
            }
            _listTabGroup.value.let { listTabGroup ->
                val newTabGroup = listTabGroup.map { tabGroup ->
                    val newPage = tabGroup.page.copy(
                        activeTaskList = tabGroup.page.activeTaskList.map { task ->
                            if (task.id == newTaskUiState.id) newTaskUiState.copy(
                                updatedAt = Calendar.getInstance().timeInMillis,
                                stringUpdatedAt = Calendar.getInstance().time.toString()
                            ) else task
                        }.sortedByDescending { it.updatedAt },
                        completedTaskList = tabGroup.page.completedTaskList.map { task ->
                            if (task.id == newTaskUiState.id) newTaskUiState.copy(
                                updatedAt = Calendar.getInstance().timeInMillis,
                                stringUpdatedAt = Calendar.getInstance().time.toString()
                            ) else task
                        }.sortedByDescending { it.updatedAt }
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
            if (!taskRepo.updateTaskCompleted(newTaskUiState.id, newTaskUiState.isCompleted)) {
                Log.e("MainViewModel", "Failed to update task completed")
                return@launch
            }
            _listTabGroup.value.let { listTabGroup ->
                val newTabGroup = listTabGroup.map { tabGroup ->
                    val sumList = tabGroup.page.activeTaskList + tabGroup.page.completedTaskList
                    val updatedList = sumList.map { task ->
                        if (task.id == newTaskUiState.id) {
                            val newUpdatedAt = Calendar.getInstance().timeInMillis
                            newTaskUiState.copy(
                                updatedAt = newUpdatedAt,
                                stringUpdatedAt = newUpdatedAt.millisToDateString()
                            )
                        } else {
                            task
                        }
                    }
                    val newPage = tabGroup.page.copy(
                        activeTaskList = updatedList.filter { !it.isCompleted }.sortedByDescending { it.updatedAt },
                        completedTaskList = updatedList.filter { it.isCompleted }.sortedByDescending { it.updatedAt }
                    )
                    tabGroup.copy(page = newPage)
                }
                _listTabGroup.value = newTabGroup
            }
        }
    }

    override fun addNewTask(collectionId: Long, content: String) {
        viewModelScope.launch {
            taskRepo.addTask(content, collectionId)?.let { taskEntity ->
                val newTaskUiState = taskEntity.toTaskUiState()
                _listTabGroup.value.let { listTabGroup ->
                    val newTabGroup = listTabGroup.map { tabGroup ->
                        if (tabGroup.tab.id == collectionId) {
                            val newPage = tabGroup.page.copy(
                                activeTaskList = (tabGroup.page.activeTaskList + newTaskUiState).sortedByDescending {
                                    it.updatedAt
                                }
                            )
                            tabGroup.copy(page = newPage)
                        } else {
                            tabGroup
                        }
                    }
                    _listTabGroup.value = newTabGroup
                }
            }
        }
    }

    override fun addNewTaskToCurrentCollection(content: String) {
        viewModelScope.launch {
            _listTabGroup.value.getOrNull(_currentSelectedCollectionIndex)?.let { currentTab ->
                val collectionId = currentTab.tab.id
                addNewTask(collectionId, content)
            }
        }
    }

    override fun updateCurrentCollectionIndex(index: Int) {
        _currentSelectedCollectionIndex = index
        Log.d("MainViewModel", "Current selected collection index: $_currentSelectedCollectionIndex")
    }

    override fun addNewCollection(title: String) {
        viewModelScope.launch {
            taskRepo.addTaskCollection(title)?.let { taskCollection ->
                val tabUiState = taskCollection.toTabUiState()
                val newTabGroup = TaskGroupUiState(tabUiState, TaskPageUiState(emptyList(), emptyList()))
                _listTabGroup.value += newTabGroup
            }
        }
    }

    override fun requestAddNewCollection() {
        viewModelScope.launch {
            _eventFlow.emit(MainEvent.RequestAddNewCollection)
        }
    }
}

interface TaskDelegate {
    fun invertTaskFavorite(taskUiState: TaskUiState) = Unit
    fun invertTaskCompleted(taskUiState: TaskUiState) = Unit
    fun addNewTask(collectionId: Long, content: String) = Unit
    fun addNewTaskToCurrentCollection(content: String) = Unit
    fun updateCurrentCollectionIndex(index: Int) = Unit
    fun addNewCollection(title: String) = Unit
    fun requestAddNewCollection(): Unit = Unit
}

sealed class MainEvent {
    data object RequestAddNewCollection : MainEvent()
}