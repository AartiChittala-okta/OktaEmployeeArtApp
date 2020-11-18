package com.okta.art.signin

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.okta.art.Globals
import com.okta.jwt.AccessTokenVerifier
import com.okta.jwt.JwtVerifiers
import com.okta.oidc.AuthorizationStatus
import com.okta.oidc.OIDCConfig
import com.okta.oidc.Okta
import com.okta.oidc.ResultCallback
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.storage.SharedPreferenceStorage
import com.okta.oidc.util.AuthorizationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.RuntimeException

internal class SignInViewModel(
    application: Application
) : AndroidViewModel(application), ResultCallback<AuthorizationStatus?, AuthorizationException?> {

    private val state = MutableLiveData<SignInState>(SignInState.Idle)
    private val webClient: WebAuthClient
    private val jwtVerifier: AccessTokenVerifier

    init {
        val config = OIDCConfig.Builder()
            .clientId("0oav60ev3rj2NsSVM0h7")
            .redirectUri("com.okta.art:/callback")
            .endSessionRedirectUri("com.okta.art:/logoutCallback")
            .scopes("openid", "profile", "offline_access")
            .discoveryUri("https://qa-achittala-op1.oktapreview.com/oauth2/default/.well-known/openid-configuration")
            .create()

        webClient = Okta.WebAuthBuilder()
            .withConfig(config)
            .withContext(getApplication())
            .withStorage(SharedPreferenceStorage(getApplication()))
            .setRequireHardwareBackedKeyStore(false)
            .create()

        jwtVerifier = JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer("https://qa-achittala-op1.oktapreview.com/oauth2/default")
            .build()
    }

    fun getState(): LiveData<SignInState> = state

    fun registerActivityForCallback(activity: Activity) {
        webClient.registerCallback(this, activity)
    }

    fun signIn(activity: Activity) {
        state.postValue(SignInState.Pending)
        webClient.signIn(activity, null)
    }

    fun errorHandled() {
        state.postValue(SignInState.Idle)
    }

    override fun onSuccess(status: AuthorizationStatus) {
        if (status == AuthorizationStatus.AUTHORIZED) {
            //client is authorized.
            val tokens = webClient.sessionClient.tokens
            viewModelScope.launch {
                try {
                    val jwt = withContext(Dispatchers.IO) {
                        jwtVerifier.decode(tokens.accessToken)
                    }
                    val username = jwt.claims["sub"].toString()
                    state.postValue(
                        SignInState.SignedIn(
                            accessToken = tokens.accessToken!!,
                            username = username
                        )
                    )
                    Globals.loggedInUser.set(username)
                } catch (e: RuntimeException) {
                    throw e
                } catch (e: Exception) {
                    state.postValue(SignInState.Error("Error validating JWT."))
                }
            }
        } else if (status == AuthorizationStatus.SIGNED_OUT) {
            //this only clears the browser session.
        }
    }

    override fun onCancel() {
        state.postValue(SignInState.Idle)
    }

    override fun onError(msg: String?, error: AuthorizationException?) {
        state.postValue(SignInState.Error(msg ?: "An Error Occurred."))
    }
}
