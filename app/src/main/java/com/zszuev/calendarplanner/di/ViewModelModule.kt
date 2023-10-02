package com.zszuev.calendarplanner.di

import androidx.lifecycle.ViewModel
import com.zszuev.calendarplanner.presentation.viewmodels.CalendarViewModel
import com.zszuev.calendarplanner.presentation.viewmodels.CreateTaskViewModel
import com.zszuev.calendarplanner.presentation.viewmodels.EditTaskViewModel
import com.zszuev.calendarplanner.presentation.viewmodels.MainViewModel
import com.zszuev.calendarplanner.presentation.viewmodels.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditTaskViewModel::class)
    fun bindEditViewModel(viewModel: EditTaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTaskViewModel::class)
    fun bindCreateViewModel(viewModel: CreateTaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    fun bindCalendarViewModel(viewModel: CalendarViewModel): ViewModel

}