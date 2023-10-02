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
import com.zszuev.calendarplanner.domain.utils.CalendarHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksByDayUseCase
) : ViewModel() {

    private val _months = MutableLiveData<List<Month>>()
    val months: LiveData<List<Month>> get() = _months

    private val _selectedDay = MutableLiveData<Int>()
    val selectedDay: LiveData<Int> get() = _selectedDay

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    private val calendarHelper = CalendarHelper()

    init {
        _months.value = calendarHelper.getMonths()
    }

    fun selectMonth(month: Month) {
        val list = months.value!!.toMutableList()
        list.find { it.isSelected }?.isSelected = false
        list.find { it.name == month.name }?.isSelected = true
        _months.value = list
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun getTasks(date: String){
        viewModelScope.launch {
            _tasks.value = getTasksUseCase(date)
        }
    }
}