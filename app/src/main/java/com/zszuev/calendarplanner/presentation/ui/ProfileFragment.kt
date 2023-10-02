package com.zszuev.calendarplanner.presentation.ui

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zszuev.calendarplanner.databinding.FragmentProfileBinding
import com.zszuev.calendarplanner.domain.utils.ProfilePhotoHelper
import com.zszuev.calendarplanner.presentation.CalendarApp
import com.zszuev.calendarplanner.presentation.ViewModelFactory
import com.zszuev.calendarplanner.presentation.viewmodels.ProfileViewModel
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var tasksToday = 0
    private var tasksTomorrow = 0
    private var tasksAfterTomorrow = 0

    private val component by lazy {
        (requireActivity().application as CalendarApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }
    @Inject
    lateinit var photoHelper: ProfilePhotoHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        component.inject(this)
        setupListeners()
        setupObservers()
        initUi()
    }

    private fun initUi() {
        photoHelper.loadProfileImage(requireContext(), binding.imageViewUserPhoto)
        binding.editTextUserName.text = SpannableStringBuilder(viewModel.getUserName())

        val days = viewModel.getDayOfWeekNames()
        binding.tvTodayDayName.text = days[0].capitalize()
        binding.tvTomorrowDayName.text = days[1].capitalize()
        binding.tvAfterTomorrowDayName.text = days[2].capitalize()

        val dates = viewModel.getDateStrings()
        viewModel.getTasksByDate(dates[0], dates[1], dates[2])
    }

    private fun setupObservers() {
        viewModel.tasksToday.observe(viewLifecycleOwner) {
            tasksToday = it.size
            updateLineProgress()
        }
        viewModel.tasksTomorrow.observe(viewLifecycleOwner) {
            tasksTomorrow = it.size
            updateLineProgress()
        }
        viewModel.tasksAfterTomorrow.observe(viewLifecycleOwner) {
            tasksAfterTomorrow = it.size
            updateLineProgress()
        }
    }

    private fun setupListeners() {
        binding.imageViewUserPhoto.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun updateLineProgress() {
        val fullHeightInDp = 150 // Максимальная высота в dp

        var maxHeight = 0
        if (tasksToday > maxHeight)
            maxHeight = tasksToday
        if (tasksTomorrow > maxHeight)
            maxHeight = tasksTomorrow
        if (tasksAfterTomorrow > maxHeight)
            maxHeight = tasksAfterTomorrow

        val todayHeight = (tasksToday.toDouble() / maxHeight ) * fullHeightInDp * resources.displayMetrics.density
        val tomorrowHeight = (tasksTomorrow.toDouble() / maxHeight ) * fullHeightInDp * resources.displayMetrics.density
        val afterTomorrowHeight = (tasksAfterTomorrow.toDouble() / maxHeight ) * fullHeightInDp * resources.displayMetrics.density

        binding.lineTodayTasks.layoutParams.height = todayHeight.toInt()
        binding.lineTodayTasks.requestLayout()
        binding.lineTomorrowTasks.layoutParams.height = tomorrowHeight.toInt()
        binding.lineTomorrowTasks.requestLayout()
        binding.lineAfterTomorrowTasks.layoutParams.height = afterTomorrowHeight.toInt()
        binding.lineAfterTomorrowTasks.requestLayout()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            if (imageBitmap != null) {
                photoHelper.saveImageToStorage(requireContext(), imageBitmap)
                binding.imageViewUserPhoto.setImageBitmap(imageBitmap)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveUserName(binding.editTextUserName.text.toString())
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

}