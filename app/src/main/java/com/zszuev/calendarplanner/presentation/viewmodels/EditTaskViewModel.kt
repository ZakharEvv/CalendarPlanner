package com.zszuev.calendarplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.usecases.DeleteTaskUseCase
import com.zszuev.calendarplanner.domain.usecases.SaveTaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditTaskViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    fun saveTask(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task)
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            deleteTaskUseCase(id)
        }
    }
}