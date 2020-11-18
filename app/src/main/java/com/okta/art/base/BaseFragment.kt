package com.okta.art.base

import androidx.fragment.app.Fragment

abstract class BaseFragment<B> : Fragment() {
    protected var _binding: B? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
