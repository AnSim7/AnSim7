package com.example.myapplication


import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


//testing DAO
@RunWith(AndroidJUnit4::class)
class OnboardingDatabaseTests {

    lateinit var slideDao: SlideDao

    @Before
    fun createDb() {
        slideDao = SlideRoomDatabase.getDatabase(InstrumentationRegistry.getTargetContext()).slideDao()
    }


    //Проверка метода удаления данных с определенным типом из базы данных
    @Test
    @Throws(Exception::class)
    fun deleteSlideFromDb() {
        val type = "type"

        GlobalScope.launch {
            slideDao.deleteAll(type)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                slideDao.getSlides(type).observeForever {
                    it?.let {
                        assertEquals(0, it.size)
                    }
                }
            }
        }
    }


    //проверка корректности записанных данных в БД и полученных
    @Test
    @Throws(Exception::class)
    fun writeAndReadData() {
        val type = "type3"

        val repository = SlideRepository(slideDao, type)
        val slide = SlideEntity(55, "title", type, 14, "subtitle", "text", "link", "image")
        GlobalScope.launch {
            repository.insert(slide)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                delay(100L)
                slideDao.getSlides(type).observeForever {
                    it?.let {
                        assertEquals(slide.id, it[it.size-1].id)
                        assertEquals(slide.title, it[it.size-1].title)
                        assertEquals(slide.subtitle, it[it.size-1].subtitle)
                        assertEquals(slide.text, it[it.size-1].text)
                        assertEquals(slide.targetLink, it[it.size-1].targetLink)
                        assertEquals(slide.image, it[it.size-1].image)
                        assertEquals(slide.number, it[it.size-1].number)
                    }
                }
            }
        }
        runBlocking {
            delay(200L)
        }

    }

    //проверка корректности записанных данных в БД и полученных
    @Test
    @Throws(Exception::class)
    fun writeAndReadData2() {
        val type = "type"

        val repository = SlideRepository(slideDao, type)
        val slide = SlideEntity(111, "title", type, 14, "subtitle", "text", "link", "image")
        val slide2 = SlideEntity(1111, "title", type, 14, "subtitle", "text", "link", "image")
        // одинаковый ид
        val slide3 = SlideEntity(1111, "title", type, 14, "subtitle", "text", "link", "image")
        GlobalScope.launch {
            repository.insert(slide)
            repository.insert(slide2)
            repository.insert(slide3)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                delay(50L)
                slideDao.getSlides(type).observeForever {
                    it?.let {
                        assertEquals(2, it.size)
                    }
                }
            }
        }
        runBlocking {
            delay(30L)
        }
    }

}
