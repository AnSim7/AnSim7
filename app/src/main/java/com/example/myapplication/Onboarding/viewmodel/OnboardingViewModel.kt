package com.example.onboarding_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*


class OnboardingViewModel : ViewModel() {
    private var _data = MutableLiveData<ArrayList<Slide>>()
    val data: LiveData<ArrayList<Slide>>
        get() = _data
    private val model = OnboardingModel()

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
