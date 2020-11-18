package com.okta.art.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "art_pieces")
@TypeConverters(value = [DateConverter::class])
internal class ArtPiece(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "file") val file: String,
    @ColumnInfo(name = "upload_date") val uploadDate: Date
)
