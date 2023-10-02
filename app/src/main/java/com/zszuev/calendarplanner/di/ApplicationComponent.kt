package com.zszuev.calendarplanner.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.zszuev.calendarplanner.presentation.ui.CalendarFragment
import com.zszuev.calendarplanner.presentation.ui.CreateTaskFragment
import com.zszuev.calendarplanner.presentation.ui.EditTaskFragment
import com.zszuev.calendarplanner.presentation.ui.MainFragment
import com.zszuev.calendarplanner.presentation.ui.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: EditTaskFragment)
    fun inject(fragment: CreateTaskFragment)
    fun inject(fragment: CalendarFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}