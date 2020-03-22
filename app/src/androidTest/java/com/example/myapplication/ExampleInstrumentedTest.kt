package com.example.myapplication

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import com.google.common.base.Predicates.equalTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.util.logging.Handler


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() { // Context of the app under test.
        val appContext: Context = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.myapplication", appContext.getPackageName())
    }
}

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    lateinit var slideDao: SlideDao

    @Before
    fun createDb() {
        slideDao = SlideRoomDatabase.getDatabase(InstrumentationRegistry.getTargetContext()).wordDao()
    }


    @Test
    @Throws(Exception::class)
    fun deleteSlideFromDb() {
        val type = "type"

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                slideDao.deleteAll(type)
                slideDao.getSlides("type").observeForever {
                    it?.let {
                        assertEquals(0, it.size)
                    }
                }
            }
        }
    }


    @Test
    @Throws(Exception::class)
    fun writeAndReadData() {
        val type="type"

        val repository = SlideRepository(slideDao, type)
        val slide= SlideEntity(4,"title",type,14,"subtitle","text","link","image")
        GlobalScope.launch {
            repository.insert(slide)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                slideDao.getSlides("type").observeForever{
                    it?.let {
                        assertEquals(slide.id, it[0].id)
                        assertEquals(slide.title, it[0].title)
                        assertEquals(slide.subtitle, it[0].subtitle)
                        assertEquals(slide.text, it[0].text)
                        assertEquals(slide.targetLink, it[0].targetLink)
                        assertEquals(slide.image, it[0].image)
                        assertEquals(slide.number, it[0].number)
                    }
                }
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadData2() {
        val type="type"

        val repository = SlideRepository(slideDao, type)
        val slide= SlideEntity(4,"title",type,14,"subtitle","text","link","image")
        val slide2= SlideEntity(1,"title",type,14,"subtitle","text","link","image")
        // одинаковый ид
        val slide3= SlideEntity(1,"title",type,14,"subtitle","text","link","image")
        GlobalScope.launch {
            repository.insert(slide)
            repository.insert(slide2)
            repository.insert(slide3)
        }

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                slideDao.getSlides("type").observeForever{
                    it?.let {
                        assertEquals(2, it.size)

                    }
                }
            }
        }
    }

}