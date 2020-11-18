package com.okta.art.upload

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import coil.load
import com.okta.art.R
import com.okta.art.base.BaseFragment
import com.okta.art.databinding.FragmentUploadBinding
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UploadFragment : BaseFragment<FragmentUploadBinding>() {

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    private val viewModel: UploadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadButton.setOnClickListener {
            when (val currentState = viewModel.state.value) {
                UploadState.AwaitingSelection -> {
                    val galleryIntent = Intent(Intent.ACTION_PICK)
                    galleryIntent.type = "image/*"
                    startActivityForResult(
                        galleryIntent,
                        IMAGE_PICK_CODE
                    )
                }
                is UploadState.AwaitingUpload -> {
                    val title = binding.titleEditText.text.toString()
                    if (title.isEmpty()) {
                        binding.titleTextInputLayout.error =
                            resources.getString(R.string.title_required)
                    } else {
                        binding.titleTextInputLayout.error = ""
                        binding.titleEditText.hideKeyboard()
                        viewModel.upload(title, currentState.filename)
                        binding.titleEditText.setText("")
                    }
                }
                else -> {
                    throw IllegalStateException("Button isn't enabled.")
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                UploadState.AwaitingSelection -> {
                    binding.uploadButton.setText(R.string.select_photo)
                    binding.uploadButton.isEnabled = true
                    binding.imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
                    binding.progressBar.visibility = View.GONE
                }
                is UploadState.AwaitingUpload -> {
                    binding.uploadButton.setText(R.string.upload)
                    binding.uploadButton.isEnabled = true
                    binding.imageView.load(File(state.filename))
                    binding.progressBar.visibility = View.GONE
                }
                is UploadState.Uploading -> {
                    binding.uploadButton.setText(R.string.upload)
                    binding.uploadButton.isEnabled = false
                    binding.imageView.load(File(state.filename))
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data ?: return
            viewModel.select(uri)
        }
    }
}

private fun EditText.hideKeyboard() {
    val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(windowToken, 0)
}
