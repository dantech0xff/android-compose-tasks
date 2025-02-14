package com.creative.androidtasks.repository

import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity

/**
 * Created by dan on 10/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

interface TaskRepo {
    suspend fun getTaskCollections(): List<TaskCollection>
    suspend fun getTasksByCollectionId(collectionId: Int): List<TaskEntity>
    suspend fun addTaskCollection(title: String): TaskCollection?
    suspend fun addTask(title: String, collectionId: Int): TaskEntity?
    suspend fun updateTask(task: TaskEntity): Boolean
    suspend fun updateTaskCollection(taskCollection: TaskCollection): Boolean
}