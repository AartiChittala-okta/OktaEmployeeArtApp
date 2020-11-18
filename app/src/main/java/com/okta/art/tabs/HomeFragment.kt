package com.okta.art.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.okta.art.R
import com.okta.art.adapters.ViewPagerAdapter
import com.okta.art.base.BaseFragment
import com.okta.art.databinding.FragmentHomeBinding
import com.okta.art.gallery.GalleryFragment
import com.okta.art.upload.UploadFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
    }

    private fun setupTabs() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(UploadFragment(), "Home")
        adapter.addFragment(GalleryFragment.newInstance(true), "Fav")
        adapter.addFragment(GalleryFragment.newInstance(false), "Team")
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
        binding.tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_group_24)
    }
}
