package com.okta.art

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.okta.art.okta.OktaState
import com.okta.art.okta.OktaViewModel

class MainActivity : FragmentActivity() {
    private val viewModel: OktaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.registerActivityForCallback(this)

        viewModel.getState().observe(this) { state ->
            when (state) {
                OktaState.SignedOut -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_to_SignInFragment)
                    viewModel.signedOut()
                }
                else -> {
                    // Handled in SignInFragment.
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterActivityForCallback()
    }
}
