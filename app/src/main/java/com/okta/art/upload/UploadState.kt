package com.okta.art.upload

internal sealed class UploadState {
    object AwaitingSelection : UploadState()
    data class AwaitingUpload(val filename: String) : UploadState()
    data class Uploading(val title: String, val filename: String) : UploadState()
}
