package com.zszuev.calendarplanner.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.zszuev.calendarplanner.R
import com.zszuev.calendarplanner.databinding.ActivityMainBinding
import com.zszuev.calendarplanner.presentation.CalendarApp

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private var currentNavItem = R.id.bottom_navigation_item_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOnboarding()
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navigation_item_main -> {
                    navController.navigate(R.id.mainFragment)
                    currentNavItem = R.id.bottom_navigation_item_main
                    true
                }

                R.id.bottom_navigation_item_calendar -> {
                    navController.popBackStack(R.id.mainFragment, false)
                    navController.navigate(R.id.calendarFragment)
                    currentNavItem = R.id.bottom_navigation_item_calendar
                    true
                }

                R.id.bottom_navigation_item_profile -> {
                    navController.popBackStack(R.id.mainFragment, false)
                    navController.navigate(R.id.profileFragment)
                    currentNavItem = R.id.bottom_navigation_item_profile
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun showOnboarding() {
        val sharedPrefs = getSharedPreferences("quiz_preferences", MODE_PRIVATE)
        val isShowed = sharedPrefs.getBoolean("is_onboarding_showed", false)
        if (!isShowed) {
            val intent = Intent(applicationContext, OnboardingActivity::class.java)
            startActivity(intent)
            val editor = sharedPrefs.edit()
            editor.putBoolean("is_onboarding_showed", true)
            editor.apply()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(navController.currentDestination!!.id == R.id.mainFragment)
            binding.bottomNavigationView.selectedItemId = R.id.bottom_navigation_item_main
    }



}