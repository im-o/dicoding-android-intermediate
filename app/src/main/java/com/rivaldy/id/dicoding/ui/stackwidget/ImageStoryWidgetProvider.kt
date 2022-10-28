package com.rivaldy.id.dicoding.ui.stackwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.dicoding.BuildConfig
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.ui.home.detailstory.DetailStoryActivity


/** Created by github.com/im-o on 10/19/2022. */

class ImageStoryWidgetProvider : AppWidgetProvider() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action != null) {
            if (intent.action == DETAIL_ACTION) {
                val name = intent.getStringExtra(EXTRA_NAME)
                val description = intent.getStringExtra(EXTRA_DESCRIPTION)
                val createdAt = intent.getStringExtra(EXTRA_CREATED)
                val photoUrl = intent.getStringExtra(EXTRA_PHOTO_URL)
                val itemStory = Story(createdAt, description, null, name, photoUrl)
                val intentDetail = Intent(Intent.ACTION_VIEW)
                intentDetail.putExtra(DetailStoryActivity.EXTRA_STORY, itemStory)
                intentDetail.setClassName(BuildConfig.APPLICATION_ID, "${BuildConfig.APPLICATION_ID}.ui.home.detail_story.DetailStoryActivity")
                intentDetail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(intentDetail)
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        private const val DETAIL_ACTION = "${BuildConfig.APPLICATION_ID}.DETAIL_ACTION"
        const val EXTRA_NAME = "${BuildConfig.APPLICATION_ID}.EXTRA_NAME"
        const val EXTRA_DESCRIPTION = "${BuildConfig.APPLICATION_ID}.EXTRA_DESCRIPTION"
        const val EXTRA_CREATED = "${BuildConfig.APPLICATION_ID}.EXTRA_CREATED"
        const val EXTRA_PHOTO_URL = "${BuildConfig.APPLICATION_ID}.EXTRA_PHOTO_URL"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val views = RemoteViews(context.packageName, R.layout.image_story_widget)
            views.setRemoteAdapter(R.id.widgetStackView, intent)
            views.setEmptyView(R.id.widgetStackView, R.id.widgetNoDataTV)
            val toastIntent = Intent(context, ImageStoryWidgetProvider::class.java)
            toastIntent.action = DETAIL_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context, 0, toastIntent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE else 0
            )
            views.setPendingIntentTemplate(R.id.widgetStackView, toastPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

