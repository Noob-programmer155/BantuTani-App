package com.bantutani.application.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.R
import com.bantutani.application.databinding.ActivityMainBinding
import com.bantutani.application.ui.harga.HargaFragment
import com.bantutani.application.ui.news.NewsFragment
import com.bantutani.application.ui.scan.ScanFragment

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment())
            .commit()

        main.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }
                R.id.kamus_menu -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, KamusFragment())
//                        .commit()
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()

                }
                R.id.harga_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HargaFragment())
                        .commit()
                }
                R.id.berita_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, NewsFragment())
                        .commit()
                }
            };true
        }
        main.scan.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ScanFragment())
                .commit()
        }
    }
}