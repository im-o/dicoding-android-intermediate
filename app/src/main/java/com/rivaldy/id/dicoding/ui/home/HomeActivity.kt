package com.rivaldy.id.dicoding.ui.home

import android.view.Menu
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter by lazy { HomeAdapter { storyClicked(it) } }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initData() {
        initView()
        initObservers()
        viewModel.getStoriesApiCall()
        binding.shimmerLayout.root.startShimmer()
    }

    override fun onResume() {
        super.onResume()
        showShimmerLoading(true)
    }

    override fun onPause() {
        super.onPause()
        showShimmerLoading(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.listDataRV.adapter = homeAdapter
    }

    private fun initObservers() {
        viewModel.userStories.observe(this) {
            when (it) {
                is DataResource.Loading -> showShimmerLoading(true)
                is DataResource.Success -> showStories(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showShimmerLoading(false)
        binding.root.showSnackBar(handleApiError(failure))
    }

    private fun showStories(response: UserStoryResponse) {
        showShimmerLoading(false)
        homeAdapter.submitList(response.listStory)
        binding.noDataTV.isVisible = response.listStory?.isEmpty() == true
    }

    private fun showShimmerLoading(isLoading: Boolean) {
        binding.shimmerLayout.root.isVisible = isLoading
        if (isLoading) {
            binding.shimmerLayout.root.showShimmer(true)
        } else {
            binding.shimmerLayout.root.stopShimmer()
            binding.shimmerLayout.root.hideShimmer()
        }
    }

    private fun storyClicked(item: Story) {
        myToast(item.toString())
    }
}