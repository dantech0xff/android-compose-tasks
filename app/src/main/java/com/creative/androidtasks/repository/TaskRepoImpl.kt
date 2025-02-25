package com.creative.androidtasks.repository

import com.creative.androidtasks.database.dao.TaskDAO
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

/**
 * Created by dan on 10/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

class TaskRepoImpl(
    private val taskDAO: TaskDAO
) : TaskRepo {
    override suspend fun getTaskCollections(): List<TaskCollection> = withContext(Dispatchers.IO) {
        taskDAO.getTaskCollections()
    }

    override suspend fun getTasksByCollectionId(collectionId: Long): List<TaskEntity> = withContext(Dispatchers.IO) {
        taskDAO.getTasks(collectionId)
    }

    override suspend fun addTaskCollection(title: String): TaskCollection? = withContext(Dispatchers.IO) {
        val now = Calendar.getInstance().timeInMillis
        val taskCollection = TaskCollection(title = title, updatedAt = now, createdAt = now)
        val id = taskDAO.insertTaskCollection(taskCollection)
        if (id > 0) {
            taskCollection.copy(id = id)
        } else {
            null
        }
    }

    override suspend fun addTask(content: String, collectionId: Long): TaskEntity? = withContext(Dispatchers.IO) {
        val now = Calendar.getInstance().timeInMillis
        val task = TaskEntity(
            content = content,
            isFavorite = false,
            isCompleted = false,
            collectionId = collectionId,
            updatedAt = now,
            createdAt = now
        )
        val id = taskDAO.insertTask(task)
        if (id > 0) {
            task.copy(id = id)
        } else {
            null
        }
    }

    override suspend fun updateTask(task: TaskEntity) = withContext(Dispatchers.IO) {
        taskDAO.updateTask(task) > 0
    }

    override suspend fun updateTaskCompleted(task: TaskEntity): Boolean = withContext(Dispatchers.IO) {
        taskDAO.updateTaskCompleted(task.id, task.isCompleted) > 0
    }

    override suspend fun updateTaskFavorite(task: TaskEntity): Boolean = withContext(Dispatchers.IO) {
        taskDAO.updateTaskFavorite(task.id, task.isFavorite) > 0
    }

    override suspend fun updateTaskCollection(taskCollection: TaskCollection) = withContext(Dispatchers.IO) {
        taskDAO.updateTaskCollection(taskCollection) > 0
    }
}