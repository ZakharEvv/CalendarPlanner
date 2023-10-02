package com.zszuev.calendarplanner.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zszuev.calendarplanner.databinding.FragmentCreateTaskBinding
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.utils.CalendarHelper
import com.zszuev.calendarplanner.presentation.adapters.CheckableCalendarAdapter
import com.zszuev.calendarplanner.presentation.CalendarApp
import com.zszuev.calendarplanner.presentation.ViewModelFactory
import com.zszuev.calendarplanner.presentation.adapters.MonthAdapter
import com.zszuev.calendarplanner.presentation.viewmodels.CreateTaskViewModel
import javax.inject.Inject

class CreateTaskFragment : Fragment() {

    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as CalendarApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CreateTaskViewModel::class.java]
    }
    @Inject
    lateinit var monthAdapter: MonthAdapter
    @Inject
    lateinit var calendarAdapter: CheckableCalendarAdapter
    @Inject
    lateinit var calendarHelper: CalendarHelper

    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        component.inject(this)
        selectedMonth = calendarHelper.getCurrentMonth()
        selectedDay = calendarHelper.getCurrentDay()
        setupTimePicker()
        setupRecyclers()
        setupObservers()
        setupListeners()
        initUi()
    }

    private fun initUi() {
        binding.recyclerMonthsCreate.scrollToPosition(calendarHelper.getCurrentMonth() - 1)
        viewModel.selectDay(selectedDay)
    }

    private fun setupTimePicker() {
        binding.hourPicker.maxValue = 23
        binding.minutePicker.maxValue = 59
    }

    private fun setupRecyclers() {
        binding.recyclerMonthsCreate.adapter = monthAdapter
        binding.recyclerCalendarCreate.adapter = calendarAdapter
    }

    private fun setupObservers() {
        viewModel.months.observe(viewLifecycleOwner) {
            monthAdapter.monthList = it
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            calendarAdapter.selectedDay = it
        }
    }

    private fun setupListeners() {
        monthAdapter.onItemClickListener = {
            viewModel.selectMonth(it)
            selectedMonth = it.index
            calendarAdapter.days = it.days
        }
        calendarAdapter.onItemClickListener = {
            viewModel.selectDay(it)
            selectedDay = it
        }
        binding.btnSaveTask.setOnClickListener {
            if (checkInput())
                Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
            else {
                saveTask()
                requireActivity().onBackPressed()
            }
        }
    }

    private fun checkInput() =
        binding.editTextTaskTitle.text.isNullOrBlank() || binding.editTextTaskText.text.isNullOrBlank() || selectedDay == 0

    private fun saveTask() {
        viewModel.saveTask(
            Task(
                Task.UNDEFINED_ID,
                String.format("%02d:%02d", binding.hourPicker.value, binding.minutePicker.value),
                String.format(
                    "%02d.%02d.%04d",
                    selectedDay,
                    selectedMonth,
                    calendarHelper.getCurrentYear()
                ),
                binding.editTextTaskTitle.text.toString(),
                binding.editTextTaskText.text.toString()
            )
        )
    }
}