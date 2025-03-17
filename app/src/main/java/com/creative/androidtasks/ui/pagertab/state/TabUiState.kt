package com.creative.androidtasks.ui.pagertab.state

import com.creative.androidtasks.database.entity.SortType
import com.creative.androidtasks.database.entity.TaskCollection
import com.creative.androidtasks.database.entity.toSortType

data class TabUiState(
    val id: Long,
    val title: String,
    val sortType: SortType
)

fun TaskCollection.toTabUiState(): TabUiState {
    return TabUiState(
        id = this.id,
        title = this.title,
        sortType = this.sortType.toSortType()
    )
}