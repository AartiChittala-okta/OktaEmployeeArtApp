package com.okta.art.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface ArtPieceDao {
    @Query("SELECT * FROM art_pieces")
    fun getAll(): PagingSource<Int, ArtPiece>

    @Insert
    fun insert(artPiece: ArtPiece)

    @Query("SELECT COUNT(*) FROM art_pieces")
    fun count(): Long
}
