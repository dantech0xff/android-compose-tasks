package com.creative.androidtasks.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Created by dan on 9/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

interface AppSetting {
    val appSettingFlow: Flow<AppSettingData>
    suspend fun setIsNotificationOn(isNotificationOn: Boolean)
    suspend fun getIsNotificationOn(): Boolean
}