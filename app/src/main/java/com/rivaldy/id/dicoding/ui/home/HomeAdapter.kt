package com.rivaldy.id.dicoding.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldy.id.commons.view.LoadingProgressDialog.getCircularProgressDrawable
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.utils.UtilExtensions.toViewFromServerDate
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.RowItemStoryBinding
import com.rivaldy.id.dicoding.ui.home.detail_story.DetailStoryActivity

/** Created by github.com/im-o on 10/14/2022. */

class HomeAdapter : ListAdapter<Story, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Story) {
            binding.apply {
                nameTV.text = item.name
                descriptionTV.text = item.description
                dateCreatedTV.text = item.createdAt?.toViewFromServerDate()
                Glide.with(root.context)
                    .load(item.photoUrl)
                    .error(com.rivaldy.id.commons.R.color.colorPrimary)
                    .placeholder(getCircularProgressDrawable(root.context))
                    .into(photoIV)

                constraintLayout.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            androidx.core.util.Pair(nameTV, root.context.getString(R.string.name)),
                            androidx.core.util.Pair(descriptionTV, root.context.getString(R.string.description)),
                            androidx.core.util.Pair(dateCreatedTV, root.context.getString(R.string.date)),
                            androidx.core.util.Pair(photoIV, root.context.getString(R.string.image_story)),
                        )
                    val intent = Intent(root.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_STORY, item)
                    root.context.startActivity(intent, optionsCompat.toBundle())
                }
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