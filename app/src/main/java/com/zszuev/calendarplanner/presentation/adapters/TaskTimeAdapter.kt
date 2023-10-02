package com.zszuev.calendarplanner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zszuev.calendarplanner.databinding.TimeTaskItemBinding
import com.zszuev.calendarplanner.domain.entities.Task
import javax.inject.Inject

class TaskTimeAdapter @Inject constructor() : RecyclerView.Adapter<TaskTimeAdapter.TaskTimeViewHolder>(){

    var tasks = listOf<Task>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var onItemClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskTimeViewHolder {
        return TaskTimeViewHolder(TimeTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskTimeViewHolder, position: Int) {
        holder.bind(tasks[position], onItemClickListener)
    }

    override fun getItemCount() = tasks.size

    class TaskTimeViewHolder(private val binding: TimeTaskItemBinding) : ViewHolder(binding.root){
        fun bind(task: Task, onItemClickListener: ((Task) -> Unit)?){
            binding.tvTimeItem.text = task.time
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(task)
            }
        }
    }
}