package com.zszuev.calendarplanner.domain.utils

import com.zszuev.calendarplanner.domain.entities.Month
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CalendarHelper @Inject constructor(){

    private val months = arrayOf(
        "Jan", "Feb", "Mar", "Apr",
        "May", "June", "July", "Aug",
        "Sept", "Oct", "Nov", "Dec"
    )

    fun getCurrentDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

    fun getMonths(): List<Month> {
        val today = GregorianCalendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val currentMonth = today.get(Calendar.MONTH)

        return months.mapIndexed { index, monthName ->
            val calendar = GregorianCalendar(currentYear, index, 1)
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val isCurrentMonth = index == currentMonth

            Month(index+1, monthName, daysInMonth, isCurrentMonth)
        }
    }

    fun getCurrentMonthAndDays(): Month {
        val today = GregorianCalendar.getInstance()
        val currentMonth = today.get(Calendar.MONTH)
        val currentYear = today.get(Calendar.YEAR)

        val calendar = GregorianCalendar(currentYear, currentMonth, 1)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val currentMonthName = months[currentMonth]

        return Month(currentMonth+1, currentMonthName, daysInMonth, true)
    }

    fun getDayOfWeekNames(): List<String> {
        val dayOfWeekNames = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

        val today = GregorianCalendar.getInstance()
        dayOfWeekNames.add(dateFormat.format(today.time))

        val tomorrow = GregorianCalendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        dayOfWeekNames.add(dateFormat.format(tomorrow.time))

        val dayAfterTomorrow = GregorianCalendar.getInstance()
        dayAfterTomorrow.add(Calendar.DAY_OF_MONTH, 2)
        dayOfWeekNames.add(dateFormat.format(dayAfterTomorrow.time))

        return dayOfWeekNames
    }

    fun getDateStrings(): List<String> {
        val dateStrings = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val today = GregorianCalendar.getInstance()
        dateStrings.add(dateFormat.format(today.time))

        val tomorrow = GregorianCalendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        dateStrings.add(dateFormat.format(tomorrow.time))

        val dayAfterTomorrow = GregorianCalendar.getInstance()
        dayAfterTomorrow.add(Calendar.DAY_OF_MONTH, 2)
        dateStrings.add(dateFormat.format(dayAfterTomorrow.time))

        return dateStrings
    }
}