package com.okta.art.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.okta.art.base.BaseFragment
import com.okta.art.databinding.FragmentGalleryBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    companion object {
        fun newInstance(isFavorites: Boolean): Fragment {
            val fragment = GalleryFragment()
            val arguments = Bundle()
            arguments.putBoolean("isFavorites", isFavorites)
            fragment.arguments = arguments
            return fragment
        }
    }

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isFavorites = arguments?.getBoolean("isFavorites") ?: false

        // Create adapter for the RecyclerView
        val adapter = GalleryAdapter()
        binding.recyclerView.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        lifecycleScope.launch {
            viewModel.allGalleryPieces.collectLatest { adapter.submitData(it) }
        }
    }
}
