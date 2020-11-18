package com.okta.art

import java.util.concurrent.atomic.AtomicReference

internal object Globals {
    val applicationContext = AtomicReference<ArtApplication>()

    // This certainly isn't how I'd do it in a real real app, but it's hackweek!
    val loggedInUser = AtomicReference<String>()
}
