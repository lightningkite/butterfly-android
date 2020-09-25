package com.lightningkite.butterfly.rx

import com.lightningkite.butterfly.Escaping
import io.reactivex.disposables.Disposable

class DisposableLambda(val lambda: @Escaping() () -> Unit) : Disposable {
    var disposed: Boolean = false
    override fun isDisposed(): Boolean {
        return disposed
    }

    override fun dispose() {
        if (!disposed) {
            disposed = true
            lambda()
        }
    }
}