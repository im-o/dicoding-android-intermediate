package com.rivaldy.id.dicoding.ui.home

import android.content.Intent
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.paging.map
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilCoroutines.io
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.openActivity
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.core.utils.UtilFunctions
import com.rivaldy.id.core.utils.UtilFunctions.logE
import com.rivaldy.id.core.utils.UtilFunctions.openAlertDialog
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityHomeBinding
import com.rivaldy.id.dicoding.databinding.RowItemStoryBinding
import com.rivaldy.id.dicoding.ui.MainViewModel
import com.rivaldy.id.dicoding.ui.auth.login.LoginActivity
import com.rivaldy.id.dicoding.ui.home.addstory.AddStoryActivity
import com.rivaldy.id.dicoding.ui.home.detailstory.DetailStoryActivity
import com.rivaldy.id.dicoding.ui.home.map.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val homeAdapter by lazy { HomeAdapter { item, adapterBinding -> animationStoryClicked(item, adapterBinding) } }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initData() {
        initView()
        initClick()
        initObservers()
        io { viewModel.clearStoriesDb() }
        viewModel.getStoriesApiCall()
        binding.shimmerLayout.root.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        showShimmerLoading(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addStoryItem -> openAddStory()
            R.id.languageSettingItem -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.logoutItem -> logoutClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.listDataRV.adapter = homeAdapter
    }

    private fun initClick() {
        binding.mapFAB.setOnClickListener { openActivity(MapsActivity::class.java) }
    }

    private fun initObservers() {
        viewModel.userStories.observe(this) {
            when (it) {
                is DataResource.Loading -> showShimmerLoading(true)
                is DataResource.Success -> showStories(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
        viewModel.getStoriesPagingApiCall().observe(this) {
            homeAdapter.submitData(lifecycle, it)
            it.map { it1 ->
                logE("paging data: ${it1.name.toString()}")
            }
        }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showShimmerLoading(false)
        binding.root.showSnackBar(handleApiError(failure))
    }

    private fun showStories(response: UserStoryResponse) {
        showShimmerLoading(false)
        binding.noDataTV.isVisible = response.listStory?.isEmpty() == true
        io {
            viewModel.insertStoriesDb(response.listStory?.map {
                StoryEntity(it.id ?: "", it.name ?: "", it.createdAt ?: "", it.description ?: "", it.photoUrl ?: "")
            } as MutableList<StoryEntity>)
        }
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

    private fun animationStoryClicked(item: Story, adapterBinding: RowItemStoryBinding) {
        adapterBinding.apply {
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@HomeActivity,
                Pair(nameTV, getString(R.string.name)),
                Pair(descriptionTV, getString(R.string.description)),
                Pair(dateCreatedTV, getString(R.string.date)),
                Pair(photoIV, getString(R.string.image_story)),
            )
            val intent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.EXTRA_STORY, item)
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun logoutClicked() {
        val title = getString(R.string.log_out)
        val message = getString(R.string.message_logout)
        openAlertDialog(this, title, message, object : UtilFunctions.DialogButtonClickListener {
            override fun onPositiveButtonClick() {
                mainViewModel.clearLoginInfo()
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            override fun onNegativeButtonClick() {}
        })
    }

    private fun openAddStory() {
        val intent = Intent(this, AddStoryActivity::class.java)
        startActivityForResult.launch(intent)
    }

    private var startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (it.data != null) {
                val isSuccessAddStory = it.data?.getBooleanExtra(AddStoryActivity.EXTRA_IS_SUCCESS_ADD_STORY, false) ?: false
                if (isSuccessAddStory) viewModel.getStoriesApiCall()
            }
        }
    }
}