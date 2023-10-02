package com.zszuev.calendarplanner.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zszuev.calendarplanner.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animationOnboarding.playAnimation()

        binding.btnStartOnboarding.setOnClickListener {
            finish()
        }
    }
}