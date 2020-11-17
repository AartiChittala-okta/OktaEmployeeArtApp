package com.okta.art.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.okta.art.R

internal class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.registerActivityForCallback(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            viewModel.signIn(requireActivity())
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Idle -> {
                    // TODO: Hide loading
                }
                SignInState.Pending -> {
                    // TODO: show loading
                }
                is SignInState.SignedIn -> {
                    val welcomeText = resources.getString(R.string.welcome_user, state.username)
                    Toast.makeText(requireContext(), welcomeText, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_SignInFragment_to_FirstFragment)
                }
                is SignInState.Error -> {
                    // TODO: Show dialog.
                    viewModel.errorHandled()
                }
            }
        }
    }
}
