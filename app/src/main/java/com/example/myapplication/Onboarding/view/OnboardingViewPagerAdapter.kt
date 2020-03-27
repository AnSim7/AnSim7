package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
        if((listSlides[position].title != "") and (listSlides[position].title != "null")) {
            binding.introTitle.setText(listSlides[position].title)
        }
        if((listSlides[position].subtitle != "") and (listSlides[position].subtitle != "null")) {
            binding.introDescription.setText(listSlides[position].subtitle)
        }
        if((listSlides[position].text != "") and (listSlides[position].text != "null")) {
            binding.introText.setText(listSlides[position].text)
        }else{
            binding.introText.visibility=View.GONE
        }

        if((listSlides[position].image != "") and (listSlides[position].image != "null")) {
            GlideApp.with(context)
                .load(listSlides[position].image)
                .override(700, 700)
                .centerCrop()
                .into(binding.introImg)
        }

        if ((listSlides[position].targetLink != "") and (listSlides[position].targetLink != "null")) {
            binding.targetLink.visibility = View.VISIBLE
            binding.targetLink.setText("Перейти")

        }

        binding.targetLink.setOnClickListener {
            try {
                context.startActivity(Intent(context, Class.forName(context.getPackageName().toString()+"."+listSlides[position].targetLink)))
            }
            catch (e: Exception){
                val text = "Не удается открыть раздел"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }

        collection.addView(binding.root)
        return binding.root
    }
}