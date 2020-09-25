package com.lightningkite.butterfly.rx

import android.view.View
import android.view.ViewParent
import android.widget.AbsListView
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.lightningkite.butterfly.Escaping
import io.reactivex.disposables.Disposable

val View.removed: DisposeCondition
    get() {
        return DisposeCondition { disposable ->
            this.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View) {
                    disposable.dispose()
                    v.removeOnAttachStateChangeListener(this)
                }

                override fun onViewAttachedToWindow(v: View) {
                    v.parent?.recyclingParent()?.let {
                        v.removeOnAttachStateChangeListener(this)
                        disposable.until(it.removed)
                    }
                }
            })
        }
    }

private fun ViewParent.recyclingParent(): View? = this as? RecyclerView
    ?: this as? AdapterView<*>
    ?: this.parent?.recyclingParent()
