package com.okta.art.upload

import android.net.Uri
import androidx.lifecycle.*
import com.okta.art.Globals
import com.okta.art.database.ArtDatabaseLocator
import com.okta.art.database.ArtPiece
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class UploadViewModel : ViewModel() {
    private val _state = MutableLiveData<UploadState>(UploadState.AwaitingSelection)
    val state: LiveData<UploadState> = _state

    fun select(uri: Uri) {
        viewModelScope.launch {
            val filename = withContext(Dispatchers.IO) {
                val inputStream = Globals.applicationContext.get().contentResolver.openInputStream(uri)!!
                return@withContext ArtPiece.saveImage(inputStream)
            }

            _state.value = UploadState.AwaitingUpload(filename)
        }
    }

    fun upload(title: String, filename: String) {
        _state.value = UploadState.Uploading(title, filename)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ArtDatabaseLocator.get().artPieceDao().insert(
                    ArtPiece(
                        user = Globals.loggedInUser.get(),
                        title = title,
                        file = filename
                    )
                )
            }

            _state.value = UploadState.AwaitingSelection
        }
    }
}
