package com.zszuev.calendarplanner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zszuev.calendarplanner.databinding.TaskCalendarItemBinding
import com.zszuev.calendarplanner.domain.entities.Task
import javax.inject.Inject

class TaskCalendarAdapter @Inject constructor(): RecyclerView.Adapter<TaskCalendarAdapter.TaskCalendarViewHolder>(){

    var tasks = listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCalendarViewHolder {
        return TaskCalendarViewHolder(TaskCalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskCalendarViewHolder, position: Int) {
        holder.bind(tasks[position], onItemClickListener)
    }

    override fun getItemCount() = tasks.size

    class TaskCalendarViewHolder(private val binding: TaskCalendarItemBinding) : ViewHolder(binding.root){
        fun bind(task: Task, onItemClickListener: ((Task) -> Unit)?){
            binding.tvHeaderTaskName.text = task.title
            binding.tvTaskName.text = task.title
            binding.tvTaskTime.text = task.time
            binding.tvTaskText.text = task.text
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(task)
            }
        }
    }
}