package com.creative.androidtasks.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_collection")
data class TaskCollection(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "sort_type", defaultValue = "0")
    val sortType: Int
)

enum class SortType(val value: Int) {
    CREATED_DATE(0),
    FAVORITE(1),
}

fun Int.toSortType(): SortType {
    return when (this) {
        1 -> SortType.FAVORITE
        else -> SortType.CREATED_DATE
    }
}