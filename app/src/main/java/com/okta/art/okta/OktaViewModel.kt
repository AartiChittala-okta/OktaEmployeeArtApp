package com.okta.art.okta

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.okta.art.Globals
import com.okta.jwt.AccessTokenVerifier
import com.okta.jwt.JwtVerifiers
import com.okta.oidc.*
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.storage.SharedPreferenceStorage
import com.okta.oidc.util.AuthorizationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.RuntimeException

internal class OktaViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val state = MutableLiveData<OktaState>(OktaState.Idle)
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

    fun getState(): LiveData<OktaState> = state

    fun registerActivityForCallback(activity: Activity) {
        webClient.registerCallback(SignInCallback(), activity)
    }

    fun unregisterActivityForCallback() {
        webClient.unregisterCallback()
    }

    fun signIn(activity: Activity) {
        state.postValue(OktaState.Pending)
        webClient.signIn(activity, null)
    }

    fun signOut(activity: Activity) {
        webClient.signOut(activity, SignOutCallback())
    }

    fun errorHandled() {
        state.postValue(OktaState.Idle)
    }

    fun signedOut() {
        state.postValue(OktaState.Idle)
    }

    inner class SignInCallback : ResultCallback<AuthorizationStatus?, AuthorizationException?> {
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
                            OktaState.SignedIn(
                                accessToken = tokens.accessToken!!,
                                username = username
                            )
                        )
                        Globals.loggedInUser.set(username)
                    } catch (e: RuntimeException) {
                        throw e
                    } catch (e: Exception) {
                        state.postValue(OktaState.Error("Error validating JWT."))
                    }
                }
            }
        }

        override fun onCancel() {
            state.postValue(OktaState.Idle)
        }

        override fun onError(msg: String?, error: AuthorizationException?) {
            state.postValue(OktaState.Error(msg ?: "An Error Occurred."))
        }
    }

    inner class SignOutCallback : RequestCallback<Int, AuthorizationException> {
        override fun onSuccess(result: Int) {
            state.postValue(OktaState.SignedOut)
        }

        override fun onError(error: String?, exception: AuthorizationException?) {
            Log.d("OktaViewModel", "$error - $exception")
        }
    }
}
