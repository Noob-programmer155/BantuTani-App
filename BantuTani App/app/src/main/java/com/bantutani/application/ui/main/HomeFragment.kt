package com.bantutani.application.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bantutani.application.R
import com.bantutani.application.databinding.FragmentHomeBinding
import com.bantutani.application.ui.profile.ProfileActivity

class HomeFragment : Fragment() {
    private lateinit var home: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels { HomeFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home = FragmentHomeBinding.bind(view)
        home.profile.setOnClickListener {
            startActivity(Intent(requireContext(),ProfileActivity::class.java))
        }
        setupViewModel()
        setupLayout()
    }

    private fun setupViewModel() {
        viewModel.getUser()
        viewModel.fetchData().observe(requireActivity(), {
            if (it != null){
                home.textView.text = "Selamat Datang, \n ${it.fullName}"
            }
        })
    }

    private fun setupLayout() {
        home.apply {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
