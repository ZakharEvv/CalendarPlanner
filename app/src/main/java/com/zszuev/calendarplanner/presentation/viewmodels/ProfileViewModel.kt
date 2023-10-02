package com.zszuev.calendarplanner.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zszuev.calendarplanner.data.RepositoryImpl
import com.zszuev.calendarplanner.domain.entities.Month
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.usecases.GetTasksByDayUseCase
import com.zszuev.calendarplanner.domain.usecases.GetUserNameUseCase
import com.zszuev.calendarplanner.domain.usecases.SaveUserNameUseCase
import com.zszuev.calendarplanner.domain.utils.CalendarHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksByDayUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase
) : ViewModel() {

    private val _tasksToday = MutableLiveData<List<Task>>()
    val tasksToday: LiveData<List<Task>> get() = _tasksToday

    private val _tasksTomorrow = MutableLiveData<List<Task>>()
    val tasksTomorrow: LiveData<List<Task>> get() = _tasksTomorrow

    private val _tasksAfterTomorrow = MutableLiveData<List<Task>>()
    val tasksAfterTomorrow: LiveData<List<Task>> get() = _tasksAfterTomorrow

    private val calendarHelper = CalendarHelper()

    fun getTasksByDate(today: String, tomorrow: String, afterTomorrow: String){
        viewModelScope.launch {
            _tasksToday.value = getTasksUseCase(today)
            _tasksTomorrow.value = getTasksUseCase(tomorrow)
            _tasksAfterTomorrow.value = getTasksUseCase(afterTomorrow)
        }
    }

    fun getDayOfWeekNames(): List<String> {
        return calendarHelper.getDayOfWeekNames()
    }

    fun getDateStrings(): List<String> {
        return calendarHelper.getDateStrings()
    }

    fun saveUserName(name: String) {
        saveUserNameUseCase(name)
    }

    fun getUserName() : String {
        return getUserNameUseCase()
    }


}