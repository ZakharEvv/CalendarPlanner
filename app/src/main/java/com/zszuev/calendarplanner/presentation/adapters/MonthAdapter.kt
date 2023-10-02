package com.zszuev.calendarplanner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.MonthItemBinding
import com.zszuev.calendarplanner.domain.entities.Month
import javax.inject.Inject

class MonthAdapter @Inject constructor() : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    var monthList = listOf<Month>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemClickListener: ((Month) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(MonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(monthList[position], onItemClickListener)
    }

    override fun getItemCount() = monthList.size

    class MonthViewHolder(private val binding: MonthItemBinding) : ViewHolder(binding.root){
        fun bind(month: Month, onItemClickListener: ((Month) -> Unit)?){
            binding.tvMonthItem.text = month.name
            if (month.isSelected)
                binding.tvMonthItem.setTextColor(binding.root.context.resources.getColor(R.color.blue))
            else
                binding.tvMonthItem.setTextColor(binding.root.context.resources.getColor(R.color.black))
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(month)
            }
        }
    }
}