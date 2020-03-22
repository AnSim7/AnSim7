package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.onboarding_project.AboutSlide
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isNotCorrect() {
        assertEquals("Numbers isn't equals!", 5, 2 + 2)
    }

    @Test(expected = NullPointerException::class)
    fun nullStringTest() {
        val str: String? = null
        assertTrue(str!!.isEmpty())
    }

    @Test
    fun aboutSlideIsCorrect() {
        val slide = AboutSlide(1, "image", "subtitle", "link", "text", "title", 4)
        assertEquals(1,slide.id)
        assertEquals("image",slide.image)
        assertEquals("subtitle",slide.subtitle)
        assertEquals("link",slide.targetLink)
        assertEquals("title",slide.title)
        assertEquals("text",slide.text)
        assertEquals(4,slide.number)
    }


//    @Test fun insertAndGetUser() = runBlocking {
//        // Given a User that has been inserted into the DB
//        var application= Application()
//        val type="type"
//        val slideDao= SlideRoomDatabase.getDatabase(application).wordDao()
//        val slide=SlideEntity(4,"title",type,14,"subtitle","text","link","image")
//        slideDao.insert(slide)
//
//        // When getting the Users via the DAO
//        val slidesFromDb = slideDao.getSlides(type)
//
//        // Then the retrieved Users matches the original user object
//        assertEquals(listOf(slide), slidesFromDb)
//    }


}
