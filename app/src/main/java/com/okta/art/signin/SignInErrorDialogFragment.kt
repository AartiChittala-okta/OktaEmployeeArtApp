package com.okta.art.signin

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

internal class SignInErrorDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = SignInErrorDialogFragment()
            val arguments = Bundle()
            arguments.putString("title", title)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(requireArguments().getString("title"))
            .create()
    }
}
