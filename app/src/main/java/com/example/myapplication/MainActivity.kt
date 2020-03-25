package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.onboarding_project.TypeOnboarding
import com.example.myapplication.core.UserSession

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide() //полоска сверху
        setContentView(R.layout.activity_main)

        var userSession = UserSession(this)
        userSession.clean() //закомментировать
        if (userSession.inNeedShowOnboarding) {
            val type = TypeOnboarding.INTERCOM
            val isSubscriber = true
            val idCity = 74
            activityOnboarding(type, isSubscriber, idCity).startOnboardingActivity(this@MainActivity)
            userSession.sync(false)
            userSession.clean() //закомментировать
        }

    }
}

class activityOnboarding(
    private val type: TypeOnboarding,
    private val isSubscriber: Boolean,
    private val idCity: Int
) {
    fun startOnboardingActivity(activity: Activity) {
        Intent(activity, OnboardingActivity::class.java).apply {
            putExtra("type", this@activityOnboarding.type)
            putExtra("isSubscriber", isSubscriber)
            putExtra("idCity", idCity)
        }.let {
            activity.startActivity(it)
        }
    }

}

