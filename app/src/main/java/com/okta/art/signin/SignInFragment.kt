package com.okta.art.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.okta.art.R
import com.okta.art.base.BaseFragment
import com.okta.art.databinding.FragmentSignInBinding

internal class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.registerActivityForCallback(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            viewModel.signIn(requireActivity())
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Idle -> {
                    binding.signInButton.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                }
                SignInState.Pending -> {
                    binding.signInButton.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is SignInState.SignedIn -> {
                    val welcomeText = resources.getString(R.string.welcome_user, state.username)
                    Toast.makeText(requireContext(), welcomeText, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_SignInFragment_to_HomeFragment)
                }
                is SignInState.Error -> {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.add(SignInErrorDialogFragment.newInstance(state.message), null)
                    transaction.commit()

                    viewModel.errorHandled()
                }
            }
        }
    }
}
