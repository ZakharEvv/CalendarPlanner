package com.zszuev.calendarplanner.domain.usecases

import com.zszuev.calendarplanner.domain.repository.Repository
import javax.inject.Inject

class SaveUserNameUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(name: String){
        repository.saveUserName(name)
    }
}