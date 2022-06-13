package com.bantutani.application.ui.kamus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bantutani.application.R
import com.bantutani.application.adapter.KamusAdapter
import com.bantutani.application.databinding.FragmentKamusBinding
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KamusFragment : Fragment() {
    private lateinit var kamusBinding : FragmentKamusBinding
    private lateinit var shimmerFrameLayout : ShimmerFrameLayout
    private lateinit var viewModel : KamusViewModel
    private lateinit var kamusAdapter: KamusAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        kamusBinding = FragmentKamusBinding.bind(view)
        kamusAdapter = KamusAdapter()
        setupView()
        setupLiveData()



    }
    private fun setupLiveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.kamusData.collectLatest {
                kamusAdapter.submitData(it)
            }
        }
    }

    private fun setupView() {
        kamusBinding.apply {
            rvKamus.layoutManager = LinearLayoutManager(activity)
            rvKamus.setHasFixedSize(true)
            rvKamus.adapter = kamusAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamus, container, false)
    }
}