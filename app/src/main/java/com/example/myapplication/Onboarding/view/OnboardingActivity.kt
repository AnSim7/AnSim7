/*
 * Copyright 2020 Симонова Анастасия
 * simonova_anastasia97@mail.ru
 */


package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.onboarding_project.OnboardingViewModel
import com.example.onboarding_project.Slide
import com.example.onboarding_project.TypeOnboarding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_intro.*

class OnboardingActivity : AppCompatActivity() {

    private val viewmodel by lazy { ViewModelProvider(this).get(OnboardingViewModel::class.java) }
    private lateinit var introViewPageAdapter: OnboardingViewPagerAdapter
    private lateinit var tabIndicator: TabLayout
    private lateinit var btn_next: Button
    private lateinit var btn_start: Button
    private lateinit var btn_skip: Button
    private lateinit var btn_anim: Animation
    private lateinit var progress_bar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeOnboarding = intent.getSerializableExtra("type") as TypeOnboarding
        val isSubsciber = intent.getBooleanExtra("isSubscriber", false)
        val idCity = intent.getIntExtra("idCity", 0)

        setContentView(R.layout.activity_intro)

        supportActionBar?.hide() //полоска сверху

        tabIndicator = findViewById(R.id.tabLayout)
        btn_next = findViewById(R.id.next)
        btn_start = findViewById(R.id.start)
        btn_anim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        progress_bar = findViewById(R.id.progressBar)
        btn_skip = findViewById(R.id.skip)

        viewmodel.readData(typeOnboarding.type, isSubsciber, idCity)

        viewmodel.data.observe(this, Observer {
            it?.let {
                progress_bar.visibility = View.GONE

                val screenPager = findViewById<View>(R.id.screen_viewpager) as ViewPager
                introViewPageAdapter = OnboardingViewPagerAdapter(this, it)

                screenPager.adapter = introViewPageAdapter

                tabIndicator.setupWithViewPager(screenPager)

                val it_check: ArrayList<Slide> = it
                btn_skip.setOnClickListener {
                    //it_check[screen_viewpager.currentItem].targetLink
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                }
                btn_next.setOnClickListener {
                    if (screen_viewpager.currentItem < it_check.size) {
                        ++screen_viewpager.currentItem
                        screenPager.setCurrentItem(screen_viewpager.currentItem)
                        loadScreen()
                    }

                    if (screen_viewpager.currentItem == it_check.size - 1) loadLastScreen()
                }

                tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {}

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab?.position == it_check.size - 1) {
                            loadLastScreen()
                        } else loadScreen()
                    }
                })

                //it_check[screen_viewpager.currentItem].targetLink
                btn_start.setOnClickListener {
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                }
            }
        })
    }

    fun loadLastScreen() {
        btn_start.visibility = View.VISIBLE
        btn_next.visibility = View.INVISIBLE
    }

    fun loadScreen() {
        btn_start.visibility = View.INVISIBLE
        btn_next.visibility = View.VISIBLE
        tabIndicator.visibility = View.VISIBLE
    }
}
