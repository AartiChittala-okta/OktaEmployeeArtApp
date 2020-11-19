package com.okta.art

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.okta.art.base.BaseFragment
import com.okta.art.database.ArtDatabaseLocator
import com.okta.art.databinding.FragmentFullscreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
internal class FullscreenFragment : BaseFragment<FragmentFullscreenBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artPieceId = requireArguments().getInt("artPieceId")
        viewLifecycleOwner.lifecycleScope.launch {
            val artPiece = withContext(Dispatchers.IO) {
                ArtDatabaseLocator.get().artPieceDao().findById(artPieceId)
            }
            binding.fullscreenContent.load(File(artPiece.file))
        }
    }
}
