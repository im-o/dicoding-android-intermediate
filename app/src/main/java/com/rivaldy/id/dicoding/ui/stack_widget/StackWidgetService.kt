package com.rivaldy.id.dicoding.ui.stack_widget

import android.content.Intent
import android.widget.RemoteViewsService

/** Created by github.com/im-o on 10/19/2022. */

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = StackRemoteViewsFactory(this.applicationContext)
}