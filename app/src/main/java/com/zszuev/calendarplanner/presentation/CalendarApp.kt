package com.zszuev.calendarplanner.presentation

import android.app.Application
import com.zszuev.calendarplanner.di.DaggerApplicationComponent

class CalendarApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}