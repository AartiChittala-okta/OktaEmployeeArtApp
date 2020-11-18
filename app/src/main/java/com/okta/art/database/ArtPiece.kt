package com.okta.art.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.okta.art.Globals
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.InputStream
import java.util.*

@Entity(tableName = "art_pieces")
@TypeConverters(value = [DateConverter::class])
internal data class ArtPiece(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "file") val file: String,
    @ColumnInfo(name = "upload_date") val uploadDate: Date = Date()
) {
    companion object {
        fun saveImage(inputStream: InputStream): String {
            val source = inputStream.source().buffer()
            val uuid = UUID.randomUUID()
            val dir = Globals.applicationContext.get().dataDir
            val filename = "/${dir.absolutePath}/$uuid"
            val sink = File(filename).sink().buffer()
            source.request(Long.MAX_VALUE)
            sink.writeAll(source)
            source.close()
            sink.close()
            return filename
        }
    }
}
