


package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onboarding_project.TypeOnboarding
import com.example.myapplication.core.UserSession

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userSession = UserSession(this)
        userSession.clean() //закомментировать
        if (userSession.inNeedShowOnboarding) {
            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            intent.putExtra("type", TypeOnboarding.SETTINGS)
            val isSubscriber = true
            intent.putExtra("isSubscriber", isSubscriber)
            val idCity = 74
            intent.putExtra("idCity", idCity)
            startActivity(intent)
            userSession.sync(false)
            userSession.clean() //закомментировать
        }

    }
}


