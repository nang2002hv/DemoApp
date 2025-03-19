package com.example.demoandroidapp.persentation.feed.Collection

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.demoandroidapp.databinding.ItemCollectionLayoutBinding
import com.example.demoandroidapp.persentation.feed.Collection.FeedCollectionUiState.CollectionItem

object CollectionItemDiffUtilItemCallback : DiffUtil.ItemCallback<CollectionItem>() {
    override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CollectionItem, newItem: CollectionItem) =
        oldItem == newItem
}

class FeedCollectionItemAdapter(
    private val onItemClicked: (item: CollectionItem) -> Unit,
    private val requestManager: RequestManager,
) :
    ListAdapter<CollectionItem, FeedCollectionItemAdapter.VH>(
        CollectionItemDiffUtilItemCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemCollectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int): Unit = holder.bind(getItem(position))

    inner class VH(
        private val binding: ItemCollectionLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                adapterPosition.let { pos ->
                    if (pos != RecyclerView.NO_POSITION) {
                        onItemClicked(getItem(pos))
                    }
                }
            }
        }

        fun bind(item: CollectionItem) {
            binding.run {
                Log.d("FeedCollectionsViewAdapter", "loadFirstPage: $item")
                textTitle.text = item.title
                textDescription.text = item.description

                requestManager
                    .load(item.imageUrl)
                    .fitCenter()
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
            }
        }
    }
}
