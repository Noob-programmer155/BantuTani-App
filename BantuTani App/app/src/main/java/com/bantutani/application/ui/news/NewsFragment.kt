package com.bantutani.application.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bantutani.application.adapter.NewsAdapter
import com.bantutani.application.databinding.FragmentNewsBinding
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private val vm: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }
    private lateinit var NewsAdapter: NewsAdapter
    private var newsBinding: FragmentNewsBinding? = null
    private val viewModel: NewsViewModel by activityViewModels() { NewsFactory(requireActivity()) }
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        newsBinding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = newsBinding!!.root
        setupAction()
        getData()
        return root
    }

    private fun setupAction() {
        NewsAdapter = NewsAdapter(viewModel.getToken()!!)
        newsBinding!!.rvNews.setHasFixedSize(true)
        newsBinding!!.rvNews.layoutManager = LinearLayoutManager(activity)
        newsBinding!!.rvNews.adapter = NewsAdapter
    }

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newsData.collectLatest {
                NewsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsBinding = null
    }
}

//   fun setupViewModel() {
//        vm = ViewModelProvider(this).get(NewsDetailViewModel::class.java)
//        with(vm) {
//            get().observe(this, {
//                if (it != null) {
//                    adapter.setList(it)
//                }
//            })
//        }
//    }
//
////   fun setupViewModel() {
////        vm = ViewModelProvider(this).get(NewsDetailViewModel::class.java)
////        with(vm) {
////            get().observe(this, {
////                if (it != null) {
////                    adapter.setList(it)
////                }
////            })
////        }
////   }
