package com.example.myapplication.Onboarding.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.onboarding_project.OnboardingModel
import com.example.onboarding_project.AboutSlide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideRepository(private val slideDao: SlideDao, val type: String) {

    val allSlides: LiveData<List<SlideEntity>> = slideDao.getSlides(type)

    private var _infoAboutSlides = MutableLiveData<ArrayList<AboutSlide>>()
    val infoAboutSlides: LiveData<ArrayList<AboutSlide>>
        get() = _infoAboutSlides
    private val modelFirestore = OnboardingModel()


    suspend fun insert(word: SlideEntity) {
        slideDao.insert(word)
    }

    fun readData(
        type: String,
        isAbonent: Boolean,
        idCity: Int
    ) {
        modelFirestore.loadData(type, isAbonent, idCity,
            { arr ->
                Unit
                GlobalScope.launch {
                    slideDao.deleteAll(type)
                    GlobalScope.launch {
                        for (value in arr) {
                            insert(
                                SlideEntity(
                                    value.id,
                                    value.title,
                                    type,
                                    value.number,
                                    value.subtitle,
                                    value.text,
                                    value.targetLink,
                                    value.image
                                )
                            )
                            _infoAboutSlides.postValue(arr)
                        }
                    }

                }
            },
            { exception ->
                Unit
                _infoAboutSlides.postValue(arrayListOf<AboutSlide>())
                Log.d("!!!", "Oops, smth went wrong")
            }
        )
    }


}