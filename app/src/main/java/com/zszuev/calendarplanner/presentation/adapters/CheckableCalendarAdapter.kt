package com.zszuev.calendarplanner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.CalendarItemBinding
import javax.inject.Inject

class CheckableCalendarAdapter @Inject constructor() :
    RecyclerView.Adapter<CheckableCalendarAdapter.CalendarViewHolder>() {

    var days = 30
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedDay = -1
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            CalendarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(position + 1, onItemClickListener, selectedDay)
    }

    override fun getItemCount() = days

    class CalendarViewHolder(private val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Int, onItemClickListener: ((Int) -> Unit)?, selectedDay: Int) {
            if (day == selectedDay) {
                binding.root.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.selected_day_bg)
                binding.tvDateCalendarItem.setTextColor(binding.root.resources.getColor(R.color.blue))
            } else {
                binding.root.background = ContextCompat.getDrawable(binding.root.context, android.R.color.transparent)
                binding.tvDateCalendarItem.setTextColor(binding.root.resources.getColor(R.color.calendar_text_color))
            }
            binding.tvDateCalendarItem.text = day.toString()
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(day)
            }
        }

    }
}