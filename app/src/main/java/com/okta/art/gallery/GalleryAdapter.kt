package com.okta.art.gallery

import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.okta.art.database.ArtPiece

internal class GalleryAdapter(
    private val navController: NavController
) : PagingDataAdapter<ArtPiece, ArtPieceViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ArtPieceViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtPieceViewHolder {
        return ArtPieceViewHolder(parent, navController)
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<ArtPiece>() {
            override fun areItemsTheSame(oldItem: ArtPiece, newItem: ArtPiece): Boolean =
                oldItem.uid == newItem.uid

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: ArtPiece, newItem: ArtPiece): Boolean =
                oldItem == newItem
        }
    }
}
