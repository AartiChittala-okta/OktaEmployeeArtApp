package com.okta.art.okta

internal sealed class OktaState {
    object SignedOut : OktaState()
    object Idle : OktaState()
    object Pending : OktaState()
    data class SignedIn(val accessToken: String, val username: String) : OktaState()
    data class Error(val message: String) : OktaState()
}
