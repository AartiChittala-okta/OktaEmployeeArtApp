package com.okta.art.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.okta.art.R
import com.okta.art.database.ArtPiece

internal class ArtPieceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.art_piece_item, parent, false)
) {
    private val nameTextView = itemView.findViewById<TextView>(R.id.title_text_view)
    private val userTextView = itemView.findViewById<TextView>(R.id.user_text_view)
    private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
    private var artPiece: ArtPiece? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(artPiece: ArtPiece?) {
        this.artPiece = artPiece

        if (artPiece == null) return

        nameTextView.text = artPiece.title
        userTextView.text = artPiece.user
    }
}
