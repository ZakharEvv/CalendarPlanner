package com.zszuev.calendarplanner.presentation.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.FragmentCreateTaskBinding
import com.zszuev.calendarplanner.databinding.FragmentEditTaskBinding
import com.zszuev.calendarplanner.domain.entities.Task
import com.zszuev.calendarplanner.presentation.CalendarApp
import com.zszuev.calendarplanner.presentation.ViewModelFactory
import com.zszuev.calendarplanner.presentation.viewmodels.EditTaskViewModel
import javax.inject.Inject

class EditTaskFragment : Fragment() {

    private lateinit var task: Task

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as CalendarApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EditTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parseParams()
        component.inject(this)
        refreshUi()
        setupListeners()
    }

    private fun parseParams() {
        arguments?.let {
            task = it.getParcelable(TASK)!!
        }
    }

    private fun refreshUi() {
        binding.hourPicker.maxValue = 23
        binding.minutePicker.maxValue = 59
        binding.hourPicker.value = task.time.substringBefore(":").toInt()
        binding.minutePicker.value = task.time.substringAfterLast(":").toInt()
        binding.editTextTaskTitle.text = SpannableStringBuilder(task.title)
        binding.editTextTaskText.text = SpannableStringBuilder(task.text)
    }

    private fun setupListeners() {
        binding.btnSaveTask.setOnClickListener {
            if (checkInput())
                Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
            else {
                saveTask()
                requireActivity().onBackPressed()
            }
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteTask(task.id)
            requireActivity().onBackPressed()
        }
    }

    private fun checkInput() =
        binding.editTextTaskTitle.text.isNullOrBlank() || binding.editTextTaskText.text.isNullOrBlank()

    private fun saveTask() {
        viewModel.saveTask(
            task.copy(
                time = String.format("%02d:%02d", binding.hourPicker.value, binding.minutePicker.value),
                title = binding.editTextTaskTitle.text.toString(),
                text = binding.editTextTaskText.text.toString()
            )
        )
    }

    companion object {

        const val TASK = "task"
    }
}