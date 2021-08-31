package com.lightningkite.butterfly.views

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 *
 *  When set to true will reverse the direction of the recycler view.
 *  Rather than top to bottom, it will scroll bottom to top.
 *
 */
var RecyclerView.reverseDirection: Boolean
    get() = (this.layoutManager as? LinearLayoutManager)?.reverseLayout ?: false
    set(value) {
        (this.layoutManager as? LinearLayoutManager)?.reverseLayout = value
    }

/**
 *
 *  Provides the RecyclerView a lambda to call when the lambda reaches the end of the list.
 *
 */

fun RecyclerView.whenScrolledToEnd(action: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            (layoutManager as? LinearLayoutManager)?.let {
                if (it.findLastVisibleItemPosition() == adapter?.itemCount?.minus(1)) {
                    action()
                }
            }
        }
    })
}