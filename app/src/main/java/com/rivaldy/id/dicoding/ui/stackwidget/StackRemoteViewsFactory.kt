package com.rivaldy.id.dicoding.ui.stackwidget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import com.rivaldy.id.core.data.datasource.local.db.DbRepositoryImpl
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.di.DatabaseModule
import com.rivaldy.id.dicoding.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutionException


/** Created by github.com/im-o on 10/19/2022. */

class StackRemoteViewsFactory internal constructor(
    private val mContext: Context
) : RemoteViewsFactory {

    private var moviesList: MutableList<StoryEntity?> = mutableListOf()
    private lateinit var repository: DbRepositoryImpl

    override fun onCreate() {
        val db = DatabaseModule.provideAppDatabase(mContext)
        repository = DbRepositoryImpl(db)
    }

    override fun onDataSetChanged() {
        val identifyToken: Long = Binder.clearCallingIdentity()
        runBlocking(Dispatchers.IO) {
            try {
                moviesList = repository.getStoriesNoLiveData().toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Binder.restoreCallingIdentity(identifyToken)
    }

    override fun onDestroy() {}

    override fun getCount(): Int = moviesList.size

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.row_item_widget)
        if (moviesList.isNotEmpty()) {
            val urlImg = moviesList[position]?.photoUrl
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
        extras.putString(ImageStoryWidgetProvider.EXTRA_NAME, moviesList[position]?.name)
        extras.putString(ImageStoryWidgetProvider.EXTRA_DESCRIPTION, moviesList[position]?.description)
        extras.putString(ImageStoryWidgetProvider.EXTRA_CREATED, moviesList[position]?.createdAt)
        extras.putString(ImageStoryWidgetProvider.EXTRA_PHOTO_URL, moviesList[position]?.photoUrl)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.imageStoryIV, fillInIntent)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}