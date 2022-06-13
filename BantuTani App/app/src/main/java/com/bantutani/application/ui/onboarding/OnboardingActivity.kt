package com.bantutani.application.ui.onboarding


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bantutani.application.adapter.OnboardingAdapter
import com.bantutani.application.databinding.ActivityOnboardingBinding
import com.bantutani.application.ui.login.LoginActivity


class OnboardingActivity : AppCompatActivity() {
    private lateinit var onboarding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboarding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(onboarding.root)

        viewPager = onboarding.slideViewPager
        adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter
        onboarding.dotsIndicator.attachTo(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    onboarding.btnMulai.visibility = android.view.View.GONE
                    onboarding.skipButton.visibility = android.view.View.VISIBLE
                } else if (position !== 3) {
                    onboarding.btnMulai.visibility = android.view.View.GONE
                    onboarding.skipButton.visibility = android.view.View.GONE
                } else {
                    onboarding.btnMulai.visibility = android.view.View.VISIBLE
                    onboarding.skipButton.visibility = android.view.View.GONE
                }

            }
        })

        onboarding.skipButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        onboarding.btnMulai.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}