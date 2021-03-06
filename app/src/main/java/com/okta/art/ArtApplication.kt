package com.okta.art

import android.app.Application
import com.okta.art.database.ArtDatabaseLocator

internal class ArtApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Globals.applicationContext.set(this)
        ArtDatabaseLocator.initialize(this)
    }
}
