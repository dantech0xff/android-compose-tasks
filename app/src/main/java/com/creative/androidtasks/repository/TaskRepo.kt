package com.creative.androidtasks.repository

import com.creative.androidtasks.database.entity.SortType
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity

/**
 * Created by dan on 10/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

interface TaskRepo {
    suspend fun getTaskCollections(): List<TaskCollection>
    suspend fun getTasksByCollectionId(collectionId: Long): List<TaskEntity>
    suspend fun addTaskCollection(title: String): TaskCollection?
    suspend fun addTask(content: String, collectionId: Long): TaskEntity?
    suspend fun updateTask(task: TaskEntity): Boolean
    suspend fun updateTaskCompleted(taskId: Long, isCompleted: Boolean): Boolean
    suspend fun updateTaskFavorite(taskId: Long, isFavorite: Boolean): Boolean
    suspend fun updateTaskCollection(taskCollection: TaskCollection): Boolean
    suspend fun deleteTaskCollectionById(collectionId: Long): Boolean
    suspend fun updateCollectionSortType(collectionId: Long, sortType: SortType): Boolean
}