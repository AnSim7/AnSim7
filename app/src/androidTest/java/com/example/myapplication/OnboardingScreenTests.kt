package com.example.myapplication

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.pager.KViewPager
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.tabs.KTabLayout
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.myapplication.Onboarding.model.SlideDao
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.Onboarding.model.SlideRoomDatabase
import com.example.myapplication.Onboarding.repository.SlideRepository
import com.example.onboarding_project.TypeOnboarding
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


class IntroScreen : Screen<IntroScreen>() {

    val skip: KButton =
        KButton { withId(R.id.skip) }

    val next: KButton =
        KButton { withId(R.id.next) }

    val start: KButton =
        KButton { withId(R.id.start) }

    val tabLayout: KTabLayout =
        KTabLayout { withId(R.id.tabLayout) }

    val viepager: KViewPager = KViewPager {
        withId(R.id.screen_viewpager)
    }
}

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTests {

    @JvmField
    @Rule
    var rule: ActivityTestRule<OnboardingActivity> =
        object : ActivityTestRule<OnboardingActivity>
            (OnboardingActivity::class.java) {

            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry
                    .getInstrumentation().targetContext
                val result = Intent(targetContext, OnboardingActivity::class.java)
                result.putExtra("type", TypeOnboarding.INTERCOM)
                val isSubscriber = true
                result.putExtra("isSubscriber", isSubscriber)
                val idCity = 74
                result.putExtra("idCity", idCity)

                return result
            }
        }


    private val introScreen = IntroScreen()
    val countOfSlide = 4

    //проверка корректности отображения активити при его создании
    @Test
    fun initialViewsDisplayedProperly() {
        introScreen {
            skip { isDisplayed() }
            next { isDisplayed() }
            tabLayout { isDisplayed() }
            start { isNotDisplayed() }
        }
    }

    //проверка корректности появления кнокпи "попробовать" при достижении последнего слайда при помощи кнопки "далее"
    @Test
    fun checkClickNext() {
        introScreen {
            sleep(10000)
            var coutSlides = countOfSlide
            while (coutSlides > 1) {
                next { click() }
                --coutSlides
            }
            next { isNotDisplayed() }
            start { isDisplayed() }
        }
    }

    //проверка корректности появления кнокпи "попробовать" при достижении последнего слайда при помощи прокрутки влево
    @Test
    fun checkViewPager() {
        introScreen {
            //если будем ждать данные с firestore то код ниже вып раньше, если выкл инет и брать данные с бд то все ок
            //или так
            sleep(10000)
            var coutSlides = countOfSlide
            while (coutSlides > 1) {
                viepager { perform { swipeLeft() } }
                --coutSlides
            }
            next { isNotDisplayed() }
            start { isDisplayed() }
            viepager { perform { swipeRight() } }
            next { isDisplayed() }
            start { isNotDisplayed() }
        }
    }

    //проверка коректности работы кнопки "пропустить"
    @Test
    fun checkSkip() {
        introScreen {
            skip { click() }
            rule.finishActivity()
        }
    }
}