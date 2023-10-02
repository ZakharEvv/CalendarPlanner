package com.zszuev.calendarplanner.data.mappers

import com.zszuev.calendarplanner.data.dbmodels.TaskDBModel
import com.zszuev.calendarplanner.domain.entities.Task
import javax.inject.Inject

class TaskMapper @Inject constructor() {

    fun mapEntityToDBModel(task: Task): TaskDBModel {
        return TaskDBModel(
            task.id,
            task.time,
            task.date,
            task.title,
            task.text
        )
    }

    fun mapDBModelToEntity(taskDBModel: TaskDBModel): Task {
        return Task(
            taskDBModel.id,
            taskDBModel.time,
            taskDBModel.date,
            taskDBModel.title,
            taskDBModel.text
        )
    }

    fun mapListDBModelToListEntity(list: List<TaskDBModel>) = list.map { mapDBModelToEntity(it) }
}