package com.rivaldy.id.dicoding.ui.stackwidget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import com.rivaldy.id.core.data.datasource.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.di.NetworkModule.baseUrl
import com.rivaldy.id.core.di.NetworkModule.providesConverterFactory
import com.rivaldy.id.core.di.NetworkModule.providesLoggingInterceptor
import com.rivaldy.id.core.di.NetworkModule.providesNetworkConnectionInterceptor
import com.rivaldy.id.core.di.NetworkModule.providesOkHttpClient
import com.rivaldy.id.core.di.NetworkModule.providesRetrofit
import com.rivaldy.id.core.di.PreferenceModule.provideSharedPreference
import com.rivaldy.id.dicoding.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutionException


/** Created by github.com/im-o on 10/19/2022. */

class StackRemoteViewsFactory internal constructor(
    private val mContext: Context
) : RemoteViewsFactory {

    private var storyList: MutableList<Story?> = mutableListOf()
    private lateinit var apiRepository: RestApiRepositoryImpl

    override fun onCreate() {
        apiRepository = RestApiRepositoryImpl(apiService())
    }

    override fun onDataSetChanged() {
        val identifyToken: Long = Binder.clearCallingIdentity()
        runBlocking(Dispatchers.IO) {
            try {
                apiRepository.getStoriesApiCall(null, null, null).apply {
                    if (this is DataResource.Success) {
                        this.value.let {
                            storyList = it.listStory?.toMutableList() ?: mutableListOf()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Binder.restoreCallingIdentity(identifyToken)
    }

    override fun onDestroy() {}

    override fun getCount(): Int = storyList.size

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.row_item_widget)
        if (storyList.isNotEmpty()) {
            val urlImg = storyList[position]?.photoUrl
            if (urlImg != null) {
                try {
                    val bitmap = Glide.with(mContext).asBitmap().load(urlImg).submit().get()
                    remoteViews.setImageViewBitmap(R.id.imageStoryIV, bitmap)
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val extras = Bundle()
        extras.putString(ImageStoryWidgetProvider.EXTRA_NAME, storyList[position]?.name)
        extras.putString(ImageStoryWidgetProvider.EXTRA_DESCRIPTION, storyList[position]?.description)
        extras.putString(ImageStoryWidgetProvider.EXTRA_CREATED, storyList[position]?.createdAt)
        extras.putString(ImageStoryWidgetProvider.EXTRA_PHOTO_URL, storyList[position]?.photoUrl)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.imageStoryIV, fillInIntent)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    private fun apiService(): ApiService {
        val pref = PreferenceRepositoryImpl(provideSharedPreference(mContext))
        val networkConnectionInterceptor = providesNetworkConnectionInterceptor(mContext)
        val okHttpClient = providesOkHttpClient(providesLoggingInterceptor(), networkConnectionInterceptor, pref)
        val retrofit = providesRetrofit(baseUrl, providesConverterFactory(), okHttpClient)
        return retrofit.create(ApiService::class.java)
    }
}