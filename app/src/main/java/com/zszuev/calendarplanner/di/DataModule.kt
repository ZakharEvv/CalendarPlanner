package com.zszuev.calendarplanner.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.zszuev.calendarplanner.data.RepositoryImpl
import com.zszuev.calendarplanner.domain.repository.Repository
import com.zszuev.taskmanager.data.database.Database
import com.zszuev.taskmanager.data.database.TasksDao
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @Provides
        fun provideTasksDao(application: Application): TasksDao {
            return Database.getInstance(application).tasksDao()
        }

        @Provides
        fun providePreferences(application: Application): SharedPreferences{
            return application.getSharedPreferences("calendar_prefs", Context.MODE_PRIVATE)
        }
    }
}