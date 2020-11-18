package com.okta.art.database

import com.okta.art.Globals
import com.okta.art.R

internal object DatabaseSeeder {
    fun seed() {
        val dao = ArtDatabaseLocator.get().artPieceDao()

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Cookies",
                file = ArtPiece.saveImage(
                    Globals.applicationContext.get().resources.openRawResource(
                        R.raw.cookies
                    )
                )
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Dog",
                file = ArtPiece.saveImage(
                    Globals.applicationContext.get().resources.openRawResource(
                        R.raw.dog
                    )
                )
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Elephant",
                file = ArtPiece.saveImage(
                    Globals.applicationContext.get().resources.openRawResource(
                        R.raw.elephant
                    )
                )
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Bird",
                file = ArtPiece.saveImage(
                    Globals.applicationContext.get().resources.openRawResource(
                        R.raw.kestrel_bird
                    )
                )
            )
        )

        dao.insert(
            ArtPiece(
                user = "aarti.chittala@okta.com",
                title = "Purple",
                file = ArtPiece.saveImage(
                    Globals.applicationContext.get().resources.openRawResource(
                        R.raw.purple
                    )
                )
            )
        )
    }
}
