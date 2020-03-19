package com.example.myapplication.Onboarding.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import com.example.onboarding_project.AboutSlide


class SlideViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: SlideRepository
    var allWords: LiveData<List<SlideEntity>> = MutableLiveData<List<SlideEntity>>()
    var arr = MutableLiveData<ArrayList<AboutSlide>>()

    private val wordsDao = SlideRoomDatabase.getDatabase(application).wordDao()


    fun loadDataFromFirestore(context: LifecycleOwner, type: String, isSubscriber: Boolean, idCity: Int){
        repository = SlideRepository(wordsDao, type)
        allWords = repository.allSlides
        repository.readData(type, isSubscriber, idCity)

        repository.infoAboutSlides.observe(context, Observer {
            it?.let {
                arr.postValue(it)
            }
        })
    }

//    fun getCountSlides(): LiveData<Int>{
//        return repository.countSlides
//    }

}