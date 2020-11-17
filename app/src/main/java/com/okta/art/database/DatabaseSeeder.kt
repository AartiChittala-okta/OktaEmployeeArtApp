package com.okta.art.database

import java.util.*

internal object DatabaseSeeder {
    fun seed() {
        val dao = ArtDatabaseLocator.get().artPieceDao()

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                file = "cookies.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                file = "dog.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                file = "elephant.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                file = "kestrel_bird.png",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                file = "purple.png",
                uploadDate = Date()
            )
        )
    }
}
