package com.creative.androidtasks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.creative.androidtasks.database.dao.TaskDAO
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity

/**
 * Created by dan on 9/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

private const val DATABASE_NAME = "app.db"
private const val DATABASE_VERSION = 1

@Database(
    entities = [TaskCollection::class, TaskEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDb : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO

    companion object {
        private var instance: AppDb? = null

        operator fun invoke(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDb = Room.databaseBuilder(
            context,
            AppDb::class.java,
            DATABASE_NAME
        ).build()
    }
}