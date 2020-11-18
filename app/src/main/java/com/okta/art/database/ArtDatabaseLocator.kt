package com.okta.art.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal object ArtDatabaseLocator {
    private lateinit var database: ArtDatabase

    fun get(): ArtDatabase = database

    fun initialize(applicationContext: Context) {
        database = Room.databaseBuilder(
            applicationContext,
            ArtDatabase::class.java, "art-database"
        ).fallbackToDestructiveMigration()
            .build()

        GlobalScope.launch {
            if (database.artPieceDao().count() == 0L) {
                DatabaseSeeder.seed()
            }
        }
    }
}
