package com.rivaldy.id.dicoding.ui.home

import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.dicoding.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter by lazy { HomeAdapter() }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initData() {
        initView()
        initObservers()
        viewModel.getStoriesApiCall()
        binding.shimmerLayout.root.startShimmer()
    }

    private fun initView() {
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
    }

    private fun showStories(response: UserStoryResponse) {
        showShimmerLoading(false)
        homeAdapter.submitList(response.listStory)
    }

    private fun showShimmerLoading(isLoading: Boolean) {
        binding.shimmerLayout.root.isVisible = isLoading
        if (isLoading) {
            binding.shimmerLayout.root.startShimmer()
        } else {
            binding.shimmerLayout.root.stopShimmer()
            binding.shimmerLayout.root.hideShimmer()
        }
    }

    override fun onResume() {
        super.onResume()
        showShimmerLoading(true)
    }

    override fun onPause() {
        super.onPause()
        showShimmerLoading(false)
    }
}