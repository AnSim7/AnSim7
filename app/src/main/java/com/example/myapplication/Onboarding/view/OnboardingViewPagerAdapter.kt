package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.onboarding_project.Slide
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp

class OnboardingViewPagerAdapter(val context: Context, val listSlides: ArrayList<Slide>): PagerAdapter() {

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view===o
    }

    override fun getCount(): Int {
        return listSlides.size
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.layout_screen, collection, false)
        val imgSlide: ImageView=layout.findViewById(R.id.intro_img)
        val title: TextView=layout.findViewById(R.id.introTitle)
        val description: TextView=layout.findViewById(R.id.introDescription)
        val text: TextView=layout.findViewById(R.id.introText)
        val targetLink: Button=layout.findViewById(R.id.targetLink)



        title.setText(listSlides[position].title)
        description.setText(listSlides[position].subtitle)
        GlideApp.with(context)
            .load(listSlides[position].image)
            .override(720, 720)
            .centerCrop()
            .into(imgSlide)
        text.setText(listSlides[position].text)

        if(listSlides[position].targetLink!=""){
            targetLink.visibility=View.VISIBLE
            targetLink.setText(listSlides[position].targetLink)
        }

        collection.addView(layout)

        return layout
    }
}