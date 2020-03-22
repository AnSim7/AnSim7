package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import com.example.myapplication.Onboarding.model.SlideEntity
import com.example.myapplication.databinding.LayoutScreenBinding


class OnboardingViewPagerAdapter(val context: Context, val listSlides: List<SlideEntity>) : PagerAdapter() {

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun getCount(): Int {
        return listSlides.size
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {

        val binding = LayoutScreenBinding.inflate(LayoutInflater.from(context), collection, false)
        binding.introTitle.setText(listSlides[position].title)
        binding.introDescription.setText(listSlides[position].subtitle)
        binding.introText.setText(listSlides[position].text)

        GlideApp.with(context)
            .load(listSlides[position].image)
            .override(720, 720)
            .centerCrop()
            .into(binding.introImg)

        if (listSlides[position].targetLink != "") {
            binding.targetLink.visibility = View.VISIBLE
            binding.targetLink.setText(listSlides[position].targetLink)
        }

        collection?.addView(binding.root)
        return binding.root
    }
}