package com.example.myapplication.Onboarding.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import com.example.onboarding_project.AboutSlide


class SlideViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: SlideRepository
    var allSlides: LiveData<List<SlideEntity>> = MutableLiveData<List<SlideEntity>>()
    var arr = MutableLiveData<ArrayList<AboutSlide>>()

    private val slideDao = SlideRoomDatabase.getDatabase(application).slideDao()


    fun loadDataFromFirestore(context: LifecycleOwner, type: String, isSubscriber: Boolean, idCity: Int) {
        repository = SlideRepository(slideDao, type)
        allSlides = repository.allSlides
        repository.readData(type, isSubscriber, idCity)

        repository.infoAboutSlides.observe(context, Observer {
            it?.let {
                arr.postValue(it)
            }
        })
    }
}