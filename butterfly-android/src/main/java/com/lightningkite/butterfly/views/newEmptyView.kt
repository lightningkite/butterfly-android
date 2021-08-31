package com.lightningkite.butterfly.views

import android.view.View
import com.lightningkite.butterfly.android.ActivityAccess

fun newEmptyView(dependency: ActivityAccess): View = View(dependency.context)