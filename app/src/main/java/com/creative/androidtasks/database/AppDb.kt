package com.creative.androidtasks.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.creative.androidtasks.database.dao.TaskDAO
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.TaskEntity

/**
 * Created by dan on 9/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

private const val DATABASE_NAME = "app.db"
private const val DATABASE_VERSION = 2

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
        ).addMigrations(MIGRATE_1_2).build()
    }
}

private val MIGRATE_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE task_collection ADD COLUMN sort_type INTEGER NOT NULL DEFAULT 0")
    }
}