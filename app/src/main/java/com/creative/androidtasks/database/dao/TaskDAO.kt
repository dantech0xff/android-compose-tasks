package com.creative.androidtasks.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity

/**
 * Created by dan on 9/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskCollection(taskCollection: TaskCollection): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Query("SELECT * FROM task_collection")
    suspend fun getTaskCollections(): List<TaskCollection>

    @Query("SELECT * FROM task WHERE collection_id = :collectionId")
    suspend fun getTasks(collectionId: Int): List<TaskEntity>

    @Query("UPDATE task SET is_favorite = :isFavorite WHERE id = :taskId")
    suspend fun updateTaskFavorite(taskId: Int, isFavorite: Boolean): Int

    @Query("UPDATE task SET is_completed = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompleted(taskId: Int, isCompleted: Boolean): Int

    @Query("UPDATE task_collection SET title = :title WHERE id = :collectionId")
    suspend fun updateTaskCollectionTitle(collectionId: Int, title: String): Int

    @Update
    suspend fun updateTaskCollection(taskCollection: TaskCollection): Int

    @Update
    suspend fun updateTask(task: TaskEntity): Int

    @Delete
    suspend fun deleteTaskCollection(taskCollection: TaskCollection): Int

    @Delete
    suspend fun deleteTask(task: TaskEntity): Int
}