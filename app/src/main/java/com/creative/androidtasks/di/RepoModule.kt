package com.creative.androidtasks.di

import com.creative.androidtasks.database.dao.TaskDAO
import com.creative.androidtasks.repository.TaskRepo
import com.creative.androidtasks.repository.TaskRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideTaskRep(taskDAO: TaskDAO): TaskRepo {
        return TaskRepoImpl(taskDAO)
    }
}