package com.creative.androidtasks.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Created by dan on 9/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

class AppSettingImpl(private val context: Context) : AppSetting {

    private val Context.dataStoreAppSetting: DataStore<Preferences>
            by preferencesDataStore(name = "app-setting-pref")

    override val appSettingFlow: Flow<AppSettingData>
        get() = context.dataStoreAppSetting.data.map { pref ->
            AppSettingData(isNotificationOn = pref[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] ?: false)
        }

    override suspend fun setIsNotificationOn(isNotificationOn: Boolean) = withContext(Dispatchers.IO) {
        context.dataStoreAppSetting.edit { pref ->
            pref[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] = isNotificationOn
        }
        Unit
    }

    override suspend fun getIsNotificationOn(): Boolean = withContext(Dispatchers.IO) {
        context.dataStoreAppSetting.data.map {
            it[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] ?: false
        }.first()
    }
}