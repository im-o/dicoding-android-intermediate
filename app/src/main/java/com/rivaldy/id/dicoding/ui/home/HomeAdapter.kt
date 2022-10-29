package com.rivaldy.id.dicoding.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldy.id.commons.view.LoadingProgressDialog.getCircularProgressDrawable
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.utils.UtilExtensions.formatDateToViewFromISO
import com.rivaldy.id.dicoding.databinding.RowItemStoryBinding

/** Created by github.com/im-o on 10/14/2022. */

class HomeAdapter(
    private val listener: (Story, RowItemStoryBinding) -> Unit
) : PagingDataAdapter<Story, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Story?) {
            binding.apply {
                nameTV.text = item?.name
                descriptionTV.text = item?.description
                dateCreatedTV.text = item?.createdAt?.formatDateToViewFromISO()
                Glide.with(root.context)
                    .load(item?.photoUrl)
                    .error(com.rivaldy.id.commons.R.color.colorPrimary)
                    .placeholder(getCircularProgressDrawable(root.context))
                    .into(photoIV)

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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}