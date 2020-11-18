package com.okta.art.database

import java.util.*

internal object DatabaseSeeder {
    fun seed() {
        val dao = ArtDatabaseLocator.get().artPieceDao()

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Cookies",
                file = "cookies.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Dog",
                file = "dog.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Elephant",
                file = "elephant.jpg",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Bird",
                file = "kestrel_bird.png",
                uploadDate = Date()
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Purple",
                file = "purple.png",
                uploadDate = Date()
            )
        )
    }
}
