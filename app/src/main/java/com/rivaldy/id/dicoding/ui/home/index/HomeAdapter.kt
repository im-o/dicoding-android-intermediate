package com.rivaldy.id.dicoding.ui.home.index

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.utils.UtilExtensions.formatDateToViewFromISO
import com.rivaldy.id.core.utils.UtilExtensions.loadImage
import com.rivaldy.id.dicoding.databinding.RowItemStoryBinding

/** Created by github.com/im-o on 10/14/2022. */

class HomeAdapter(
    private val listener: (StoryEntity, RowItemStoryBinding) -> Unit
) : PagingDataAdapter<StoryEntity, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: StoryEntity?) {
            binding.apply {
                nameTV.text = item?.name
                descriptionTV.text = item?.description
                dateCreatedTV.text = item?.createdAt?.formatDateToViewFromISO()
                photoIV.loadImage(item?.photoUrl)
                constraintLayout.setOnClickListener { listener(item ?: return@setOnClickListener, binding) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}