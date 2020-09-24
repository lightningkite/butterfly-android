package com.lightningkite.butterfly.rx

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.observables.Closeable
import io.reactivex.disposables.Disposable

class DisposeCondition(val call: @Escaping() (Disposable) -> Unit)

infix fun DisposeCondition.and(other: DisposeCondition): DisposeCondition =
    andAllDisposeConditions(listOf(this, other))

fun andAllDisposeConditions(list: List<DisposeCondition>): DisposeCondition = DisposeCondition { it ->
    var disposalsLeft = list.size
    for (item in list) {
        item.call(DisposableLambda {
            disposalsLeft -= 1
            if (disposalsLeft == 0) it.dispose()
        })
    }
}

infix fun DisposeCondition.or(other: DisposeCondition): DisposeCondition =
    DisposeCondition { it -> this.call(it); other.call(it) }
