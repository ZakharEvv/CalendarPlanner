package com.zszuev.calendarplanner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.CalendarItemBinding
import javax.inject.Inject

class CalendarAdapter @Inject constructor() : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    var days = 30
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var currentDay = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(position+1, currentDay)
    }

    override fun getItemCount() = days

    class CalendarViewHolder(private val binding: CalendarItemBinding) : ViewHolder(binding.root){
        fun bind(day: Int, currentDay: Int) {
            if (day == currentDay) {
                binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.current_day_main)
                binding.tvDateCalendarItem.setTextColor(binding.root.resources.getColor(R.color.blue))
            }
            binding.tvDateCalendarItem.text = day.toString()
        }

    }
}