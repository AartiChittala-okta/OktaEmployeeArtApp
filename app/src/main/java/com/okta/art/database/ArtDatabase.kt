package com.okta.art.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtPiece::class], version = 1)
internal abstract class ArtDatabase : RoomDatabase() {
    abstract fun artPieceDao(): ArtPieceDao
}
