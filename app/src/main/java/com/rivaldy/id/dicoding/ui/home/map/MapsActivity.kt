package com.rivaldy.id.dicoding.ui.home.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.commons.view.LoadingProgressDialog
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilConstants.DEFAULT_LIMIT_PAGE
import com.rivaldy.id.core.utils.UtilConstants.ONLY_LOCATION_TYPE
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.formatDateToViewFromISO
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityMapsBinding
import com.rivaldy.id.dicoding.ui.home.HomeViewModel
import com.rivaldy.id.dicoding.ui.home.detailstory.DetailStoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var btmSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun getViewBinding() = ActivityMapsBinding.inflate(layoutInflater)

    override fun initData() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btmSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.root)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
        setMapStyle()
        initObservers()
        initClick()
    }

    private fun initObservers() {
        viewModel.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE)
        viewModel.userStories.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showStories(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initClick() {
        mMap.setOnMapClickListener { showBottomSheet(false) }
        mMap.setOnMyLocationButtonClickListener {
            showBottomSheet(false)
            false
        }
        mMap.setOnMarkerClickListener {
            mMap.uiSettings.isMapToolbarEnabled = true
            showUserStory(it.tag as Story?)
            false
        }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        myToast(handleApiError(failure))
    }

    private fun showStories(response: UserStoryResponse) {
        showLoading(false)
        showMarkerUsers(response.listStory)
    }

    private fun setMapStyle() {
        try {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        } catch (exception: Resources.NotFoundException) {
            exception.printStackTrace()
        }
    }

    private fun showMarkerUsers(listStory: MutableList<Story>?) {
        listStory?.forEach {
            val latLng = LatLng(it.lat ?: return, it.lon ?: return)
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(it.name))
            marker?.tag = it
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels, 300))
    }

    private fun showUserStory(story: Story?) {
        if (story != null) {
            setUserStory(story)
            showBottomSheet(true)
        } else {
            showBottomSheet(false)
            mMap.uiSettings.isMapToolbarEnabled = false
        }
    }

    private fun setUserStory(item: Story) {
        binding.bottomSheetLayout.layout.apply {
            nameTV.text = item.name
            descriptionTV.text = item.description
            dateCreatedTV.text = item.createdAt?.formatDateToViewFromISO()
            Glide.with(root.context)
                .load(item.photoUrl)
                .error(com.rivaldy.id.commons.R.color.colorPrimary)
                .placeholder(LoadingProgressDialog.getCircularProgressDrawable(root.context))
                .into(photoIV)

            constraintLayout.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MapsActivity,
                    Pair(nameTV, getString(R.string.name)),
                    Pair(descriptionTV, getString(R.string.description)),
                    Pair(dateCreatedTV, getString(R.string.date)),
                    Pair(photoIV, getString(R.string.image_story)),
                )
                val intent = Intent(this@MapsActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_STORY, item)
                startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    private fun showBottomSheet(isShow: Boolean) {
        if (isShow) btmSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        else {
            if (btmSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                btmSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> if (isGranted) getMyLocation() }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}