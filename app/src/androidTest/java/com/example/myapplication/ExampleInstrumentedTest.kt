package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import com.example.myapplication.Onboarding.viewmodel.SlideViewModel
import com.example.onboarding_project.AboutSlide
import com.example.onboarding_project.OnboardingModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.lang.Thread.sleep


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
