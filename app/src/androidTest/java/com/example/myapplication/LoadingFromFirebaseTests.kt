package com.example.myapplication

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//testing load from firestore
@RunWith(AndroidJUnit4::class)
class LoadingFromFirebaseTests {
    lateinit var slideDao: SlideDao

    @Before
    fun createDb() {
        slideDao = SlideRoomDatabase.getDatabase(InstrumentationRegistry.getTargetContext()).slideDao()
    }

    //проверка коректного поведения (данные, которые должны быть получены - известны, происходить сравнение полученных с исходными)
    @Test
    @Throws(Exception::class)
    fun correctLoadFromFirestore() {
        val type = "intercom"
        val isAbonent = true
        val idCity = 74
        val repository = SlideRepository(slideDao, type)
        GlobalScope.launch {
            repository.readData(type, isAbonent, idCity)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                delay(1L)

                slideDao.getSlides(type).observeForever {
                    it?.let {
                        Assert.assertEquals(4, it.size)
                        Assert.assertEquals(15, it[0].id)
                        Assert.assertEquals(1, it[1].id)
                        Assert.assertEquals(66, it[2].id)
                        Assert.assertEquals(4, it[3].id)
                    }
                }
            }
        }
        runBlocking {
            delay(2L)
        }
    }

    //проверка считывания данных из несуществующего документа
    @Test
    @Throws(Exception::class)
    fun uncorrectLoadFromFirestore() {
        val type = "uncorrect"
        val isAbonent = true
        val idCity = 74
        val repository = SlideRepository(slideDao, type)
        GlobalScope.launch {
            repository.readData(type, isAbonent, idCity)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                delay(1L)

                slideDao.getSlides(type).observeForever {
                    it?.let {
                        Assert.assertEquals(0, it.size)
                    }
                }
            }
        }
        runBlocking {
            delay(2L)
        }
    }

}