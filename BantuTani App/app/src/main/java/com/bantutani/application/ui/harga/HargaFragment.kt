package com.bantutani.application.ui.harga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bantutani.application.R
import com.bantutani.application.adapter.CommodityAdapter
import com.bantutani.application.databinding.FragmentHargaBinding
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HargaFragment : Fragment() {
    private lateinit var hargaBinding : FragmentHargaBinding
    private lateinit var hargaAdapter: CommodityAdapter
    private lateinit var shimmerFrameLayout : ShimmerFrameLayout
    private val hargaViewModel : HargaViewModel by activityViewModels {HargaFactory(requireContext())}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hargaBinding = FragmentHargaBinding.bind(view)
        hargaAdapter = CommodityAdapter()
        setupView()
        setupLiveData()
    }

    private fun setupLiveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            hargaViewModel.hargaData.collectLatest {
                hargaAdapter.submitData(it)
            }
        }
    }

    private fun setupView() {
        hargaBinding.apply {
            rvPrice.layoutManager = LinearLayoutManager(activity)
            rvPrice.setHasFixedSize(true)
            rvPrice.adapter = hargaAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_harga, container, false)
    }
}