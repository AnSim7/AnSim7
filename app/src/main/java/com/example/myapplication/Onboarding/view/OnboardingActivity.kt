/*
 * Copyright 2020 Симонова Анастасия
 * simonova_anastasia97@mail.ru
 */


package com.example.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Onboarding.viewmodel.SlideViewModel
import com.example.myapplication.databinding.ActivityIntroBinding
import com.example.onboarding_project.TypeOnboarding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.coroutines.delay
import java.lang.Thread.sleep


class OnboardingActivity : AppCompatActivity() {

    private val slideViewModel by lazy { ViewModelProvider(this).get(SlideViewModel::class.java) }
    private lateinit var introViewPageAdapter: OnboardingViewPagerAdapter

    lateinit private var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        supportActionBar?.hide() //полоска сверху
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        binding.owner = this

        val typeOnboarding = intent.getSerializableExtra("type") as? TypeOnboarding
        val isSubsciber = intent.getBooleanExtra("isSubscriber", false)
        val idCity = intent.getIntExtra("idCity", 0)

        slideViewModel.loadDataFromFirestore(this, typeOnboarding!!.type, isSubsciber, idCity)
        slideViewModel.arr.observe(this, Observer {
            var countSlides = it.size
            it?.let {
                slideViewModel.allSlides.observe(this, Observer { slides ->
                    slides?.let {
                        if ((countSlides == 0) and (slides.size == 0)) {
                            startActivity(Intent(this@OnboardingActivity, TargetActivity::class.java))
                        } else if (countSlides == 0) {
                            countSlides = slides.size
                        }
                        introViewPageAdapter = OnboardingViewPagerAdapter(this, it)
                        binding.screenViewpager.adapter = introViewPageAdapter
                        binding.progressBar.visibility = View.GONE
                        binding.tabLayout.setupWithViewPager(binding.screenViewpager)
                        binding.skip.setOnClickListener {
                            //it_check[screen_viewpager.currentItem].targetLink
                            startActivity(Intent(this@OnboardingActivity, TargetActivity::class.java))
                        }
                        binding.next.setOnClickListener {
                            if (screen_viewpager.currentItem < countSlides) {
                                ++screen_viewpager.currentItem
                                binding.screenViewpager.setCurrentItem(binding.screenViewpager.currentItem)
                                loadScreen()
                            }
                            if (binding.screenViewpager.currentItem == countSlides - 1) loadLastScreen()
                        }

                        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                            override fun onTabReselected(tab: TabLayout.Tab?) {}

                            override fun onTabUnselected(tab: TabLayout.Tab?) {}

                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                if (tab?.position == countSlides - 1) {
                                    loadLastScreen()
                                } else {
                                    loadScreen()
                                }
                            }
                        })

                        //it_check[screen_viewpager.currentItem].targetLink
                        binding.start.setOnClickListener {
                            startActivity(Intent(this@OnboardingActivity, TargetActivity::class.java))
                        }

                    }

                })
            }
        })
    }

    fun loadLastScreen() {
        binding.start.visibility = View.VISIBLE
        binding.next.visibility = View.INVISIBLE
    }

    fun loadScreen() {
        binding.start.visibility = View.INVISIBLE
        binding.next.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
    }
}
