package com.creative.androidtasks.di

import android.content.Context
import com.creative.androidtasks.database.AppDb
import com.creative.androidtasks.database.dao.TaskDAO
import com.creative.androidtasks.repository.TaskRepo
import com.creative.androidtasks.repository.TaskRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTaskDao(appDb: AppDb): TaskDAO {
        return appDb.taskDAO()
    }

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDb {
        return AppDb.invoke(context)
    }
}