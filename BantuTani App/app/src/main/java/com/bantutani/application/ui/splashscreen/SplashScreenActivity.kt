package com.bantutani.application.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.databinding.ActivitySplashscreenBinding
import com.bantutani.application.ui.main.MainActivity
import com.bantutani.application.ui.onboarding.OnboardingActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splash : ActivitySplashscreenBinding
    private lateinit var handler: Handler
    private val viewModel : SplashViewModel by viewModels() {  SplashFactory(this) }
    val timer: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splash = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(splash.root)

        viewModel.getData()
        viewModel.fetchData().observe(this,{
            if (it != ""){
                handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, timer)
            }else{
                handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    val intent = Intent(this, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }, timer)
            }
        })
    }
}