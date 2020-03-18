package com.example.onboarding_project

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.MainApplication
import com.example.myapplication.room.OnboardingDbModel
import com.example.myapplication.room.OnboardingRepository
import com.example.myapplication.room.OnboardingRoomDatabase
import com.example.myapplication.room.OnboaringDao
import kotlinx.coroutines.*


class OnboardingViewModel(application: Application) : AndroidViewModel(application) {
    private var _data = MutableLiveData<ArrayList<Slide>>()
    val data: LiveData<ArrayList<Slide>>
        get() = _data
    private val model = OnboardingModel()

    private lateinit var repository: OnboardingRepository
    private lateinit var allSlides: LiveData<List<OnboardingDbModel?>?>

    init{
        val onboaringDao= MainApplication.instance.database.onboaringDao()
        repository= OnboardingRepository(onboaringDao)
        allSlides=repository.slides
    }

    fun readData(
        type: String,
        isAbonent: Boolean,
        idCity: Int
    ) {
        model.loadData(type, isAbonent, idCity,
            { arr ->
                Unit
                _data.postValue(arr)
            },
            { exception ->
                Unit
                Log.d("!!!", "Oops, smth went wrong")
            }
        )
    }


}


