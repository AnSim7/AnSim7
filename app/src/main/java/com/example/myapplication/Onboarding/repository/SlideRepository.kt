package com.example.myapplication.Onboarding.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.onboarding_project.OnboardingModel
import com.example.onboarding_project.AboutSlide
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SlideRepository(private val slideDao: SlideDao, val type: String) {

    val allSlides: LiveData<List<SlideEntity>> = slideDao.getSlides(type)

    private var _infoAboutSlides = MutableLiveData<ArrayList<AboutSlide>>()
    val infoAboutSlides: LiveData<ArrayList<AboutSlide>>
        get() = _infoAboutSlides
    private val modelFirestore = OnboardingModel()

    private fun onCompleteLoad(arr: ArrayList<AboutSlide>) {
        GlobalScope.launch(Main) {
            withContext(IO) {
                slideDao.deleteAll(type)

                ArrayList<SlideEntity>().apply {
                    arr.forEach { aboutSlide ->
                        add(
                            SlideEntity(
                                aboutSlide.id,
                                aboutSlide.title,
                                type,
                                aboutSlide.number,
                                aboutSlide.subtitle,
                                aboutSlide.text,
                                aboutSlide.targetLink,
                                aboutSlide.image
                            )
                        )
                    }
                }.let {
                    slideDao.insert(it)
                }
            }

            _infoAboutSlides.postValue(arr)
        }
    }

    fun readData(
        type: String,
        isAbonent: Boolean,
        idCity: Int
    ) {
        modelFirestore.loadData(type, isAbonent, idCity, this::onCompleteLoad) { exception ->
            _infoAboutSlides.postValue(arrayListOf())
            Log.d("!!!", "Oops, smth went wrong")
        }
    }


}