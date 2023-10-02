package com.zszuev.calendarplanner.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.FragmentMainBinding
import com.zszuev.calendarplanner.databinding.FragmentProfileBinding
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.domain.utils.CalendarHelper
import com.zszuev.calendarplanner.presentation.CalendarApp
import com.zszuev.calendarplanner.presentation.ViewModelFactory
import com.zszuev.calendarplanner.presentation.adapters.CalendarAdapter
import com.zszuev.calendarplanner.presentation.adapters.MonthAdapter
import com.zszuev.calendarplanner.presentation.adapters.TaskTimeAdapter
import com.zszuev.calendarplanner.presentation.viewmodels.MainViewModel
import java.util.*
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as CalendarApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    @Inject
    lateinit var calendarAdapter: CalendarAdapter
    @Inject
    lateinit var todayAdapter: TaskTimeAdapter
    @Inject
    lateinit var tomorrowAdapter: TaskTimeAdapter
    @Inject
    lateinit var dayAfterTomorrowAdapter: TaskTimeAdapter

    private val navController by lazy {
        requireActivity().findNavController(R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        component.inject(this)
        refreshUi()
        setupRecyclers()
        setupObservers()
        setupListeners()
        loadTasks()
    }

    private fun loadTasks() {
        val dates = viewModel.getDateStrings()
        viewModel.getTasksByDate(dates[0], dates[1], dates[2])
    }

    private fun setupListeners() {
        binding.btnAddTask.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_createTaskFragment)
        }
        todayAdapter.onItemClickListener = {
            launchEditScreen(it)
        }
        tomorrowAdapter.onItemClickListener = {
            launchEditScreen(it)
        }
        dayAfterTomorrowAdapter.onItemClickListener = {
            launchEditScreen(it)
        }
    }

    private fun launchEditScreen(it: Task) {
        val bundle = Bundle()
        bundle.putParcelable(EditTaskFragment.TASK, it)
        navController.navigate(R.id.action_mainFragment_to_editTaskFragment, bundle)
    }

    private fun setupObservers() {
        viewModel.currentMonth.observe(viewLifecycleOwner) {
            binding.tvMonthMain.text = it.name
            calendarAdapter.days = it.days
            val calendar = Calendar.getInstance()
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            calendarAdapter.currentDay = dayOfMonth
        }
        viewModel.tasksToday.observe(viewLifecycleOwner) {
            todayAdapter.tasks = it
            if (!it.isEmpty())
                binding.todayPlaceholder.visibility = View.GONE
        }
        viewModel.tasksTomorrow.observe(viewLifecycleOwner) {
            tomorrowAdapter.tasks = it
            if (!it.isEmpty())
                binding.tomorrowPlaceholder.visibility = View.GONE
        }
        viewModel.tasksAfterTomorrow.observe(viewLifecycleOwner) {
            dayAfterTomorrowAdapter.tasks = it
            if (!it.isEmpty())
                binding.dayAfterTomorrowPlaceholder.visibility = View.GONE
        }
    }

    private fun setupRecyclers() {
        binding.recyclerCalendarMain.adapter = calendarAdapter
        binding.recyclerTodayTime.adapter = todayAdapter
        binding.recyclerTomorrowTime.adapter = tomorrowAdapter
        binding.recyclerDayAfterTomorrowTime.adapter = dayAfterTomorrowAdapter
    }

    private fun refreshUi() {
        val days = viewModel.getDayOfWeekNames()
        binding.tvTodayName.text = days[0].capitalize()
        binding.tvTomorrowName.text = days[1].capitalize()
        binding.tvDayAfterTomorrowName.text = days[2].capitalize()
    }


}