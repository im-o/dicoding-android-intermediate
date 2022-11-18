package com.rivaldy.id.dicoding.ui.home.detailstory

import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.utils.UtilExtensions.formatDateToViewFromISO
import com.rivaldy.id.core.utils.UtilExtensions.loadImage
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
                dateCreatedTV.text = extraStory?.createdAt?.formatDateToViewFromISO()
                photoIV.loadImage(extraStory?.photoUrl)
            }
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}