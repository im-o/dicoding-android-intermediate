package com.rivaldy.id.dicoding.ui.home.detail_story

import com.bumptech.glide.Glide
import com.rivaldy.id.commons.R
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.commons.view.LoadingProgressDialog
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.utils.UtilExtensions.toViewFromServerDate
import com.rivaldy.id.dicoding.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : BaseActivity<ActivityDetailStoryBinding>() {
    private var extraStory: Story? = null

    override fun getViewBinding() = ActivityDetailStoryBinding.inflate(layoutInflater)

    override fun initData() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        extraStory = intent.getParcelableExtra(EXTRA_STORY) ?: return
        if (extraStory != null) {
            binding.apply {
                nameTV.text = extraStory?.name
                descriptionTV.text = extraStory?.description
                dateCreatedTV.text = extraStory?.createdAt?.toViewFromServerDate()
                Glide.with(root.context)
                    .load(extraStory?.photoUrl)
                    .error(R.color.colorPrimary)
                    .placeholder(LoadingProgressDialog.getCircularProgressDrawable(this@DetailStoryActivity))
                    .into(photoIV)
            }
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}