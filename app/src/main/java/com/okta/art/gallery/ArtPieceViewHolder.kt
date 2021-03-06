package com.okta.art.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.okta.art.R
import com.okta.art.database.ArtPiece
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

internal class ArtPieceViewHolder(
    parent: ViewGroup,
    private val navController: NavController
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.art_piece_item, parent, false)
), View.OnClickListener {
    private val nameTextView = itemView.findViewById<TextView>(R.id.title_text_view)!!
    private val userTextView = itemView.findViewById<TextView>(R.id.user_text_view)!!
    private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
    private var artPiece: ArtPiece? = null

    init {
        itemView.setOnClickListener(this)
    }

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(artPiece: ArtPiece?) {
        this.artPiece = artPiece

        if (artPiece == null) return

        nameTextView.text = artPiece.title
        userTextView.text = userTextView.resources.getString(
            R.string.upload_by_on,
            artPiece.user,
            artPiece.uploadDate.formattedForDisplay()
        )
        imageView.load(File(artPiece.file))
    }

    override fun onClick(v: View) {
        val arguments = Bundle()
        arguments.putInt("artPieceId", artPiece!!.uid)
        navController.navigate(R.id.action_HomeFragment_to_FullScreenFragment, arguments)
    }
}

private val simpleDateFormat = object : ThreadLocal<SimpleDateFormat>() {
    override fun initialValue(): SimpleDateFormat {
        return SimpleDateFormat("MM/dd/yy", Locale.US)
    }
}

private fun Date.formattedForDisplay(): String {
    return simpleDateFormat.get().format(this)
}
