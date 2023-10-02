package com.zszuev.calendarplanner.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.FragmentCalendarBinding
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.utils.CalendarHelper
import com.zszuev.calendarplanner.presentation.CalendarApp
import com.zszuev.calendarplanner.presentation.adapters.CheckableCalendarAdapter
import com.zszuev.calendarplanner.presentation.ViewModelFactory
import com.zszuev.calendarplanner.presentation.adapters.CalendarAdapter
import com.zszuev.calendarplanner.presentation.adapters.MonthAdapter
import com.zszuev.calendarplanner.presentation.adapters.TaskCalendarAdapter
import com.zszuev.calendarplanner.presentation.viewmodels.CalendarViewModel
import javax.inject.Inject

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        requireActivity().findNavController(R.id.nav_host_fragment)
    }

    private val component by lazy {
        (requireActivity().application as CalendarApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CalendarViewModel::class.java]
    }

    @Inject
    lateinit var monthAdapter: MonthAdapter
    @Inject
    lateinit var calendarAdapter: CheckableCalendarAdapter
    @Inject
    lateinit var taskAdapter: TaskCalendarAdapter
    @Inject
    lateinit var calendarHelper: CalendarHelper

    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        component.inject(this)
        selectedMonth = calendarHelper.getCurrentMonth()
        selectedDay = calendarHelper.getCurrentDay()
        setupRecyclers()
        setupListeners()
        setupObservers()
        initUi()
    }

    private fun initUi() {
        viewModel.selectDay(selectedDay)
        viewModel.getTasks(
            String.format(
                "%02d.%02d.%04d",
                selectedDay,
                selectedMonth,
                calendarHelper.getCurrentYear()
            )
        )
        binding.recyclerMonthsCalendar.scrollToPosition(calendarHelper.getCurrentMonth() - 1)
    }

    private fun setupObservers() {
        viewModel.months.observe(viewLifecycleOwner) {
            monthAdapter.monthList = it
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            calendarAdapter.selectedDay = it
        }
        viewModel.tasks.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.dayAfterTomorrowPlaceholder.visibility = View.VISIBLE
            else
                binding.dayAfterTomorrowPlaceholder.visibility = View.GONE
            taskAdapter.tasks = it
        }
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            navController.navigate(R.id.createTaskFragment)
        }
        monthAdapter.onItemClickListener = {
            viewModel.selectMonth(it)
            selectedMonth = it.index
            calendarAdapter.days = it.days
            viewModel.getTasks(
                String.format(
                    "%02d.%02d.%04d",
                    selectedDay,
                    selectedMonth,
                    calendarHelper.getCurrentYear()
                )
            )
        }
        calendarAdapter.onItemClickListener = {
            viewModel.selectDay(it)
            selectedDay = it
            viewModel.getTasks(
                String.format(
                    "%02d.%02d.%04d",
                    selectedDay,
                    selectedMonth,
                    calendarHelper.getCurrentYear()
                )
            )
        }
        taskAdapter.onItemClickListener = {
            launchEditScreen(it)
        }
    }

    private fun launchEditScreen(it: Task) {
        val bundle = Bundle()
        bundle.putParcelable(EditTaskFragment.TASK, it)
        navController.navigate(R.id.action_calendarFragment_to_editTaskFragment, bundle)
    }

    private fun setupRecyclers() {
        binding.recyclerMonthsCalendar.adapter = monthAdapter
        binding.recyclerCalendarCalendar.adapter = calendarAdapter
        binding.recyclerTaskCalendar.adapter = taskAdapter
    }
}