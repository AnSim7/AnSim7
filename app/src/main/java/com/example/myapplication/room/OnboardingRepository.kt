package com.example.myapplication.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onboarding_project.OnboardingModel
import com.example.onboarding_project.Slide


class OnboardingRepository(
    private val onboaringDao: OnboaringDao
) {
    private var _data = MutableLiveData<ArrayList<Slide>>()
    val data: LiveData<ArrayList<Slide>>
        get() = _data
    private val model = OnboardingModel()

    val slides
        get() = onboaringDao.getAll()


    fun getSlidesDb(
        type: String,
        isAbonent: Boolean,
        idCity: Int) : LiveData<List<OnboardingDbModel?>?> {
        loadSlides(type, isAbonent, idCity)
        return slides
    }

    fun update(
        type: String,
        isAbonent: Boolean,
        idCity: Int) {
        loadSlides(type,isAbonent,idCity)
    }

    private fun loadSlides(
        type: String,
        isAbonent: Boolean,
        idCity: Int
    ) {
        model.loadData(type, isAbonent, idCity,
            { arr ->
                Unit
                _data.postValue(arr)
                for(value in _data.value!!){
                    onboaringDao.update(OnboardingDbModel.fromOnboarding(type, 1, value))
                }
            },
            { exception ->
                Unit
                Log.d("!!!", "Oops, smth went wrong")
            }
        )
    }
}

