package com.okta.art.signin

internal sealed class SignInState {
    object Idle : SignInState()
    object Pending : SignInState()
    data class SignedIn(val accessToken: String, val username: String) : SignInState()
    data class Error(val message: String) : SignInState()
}
