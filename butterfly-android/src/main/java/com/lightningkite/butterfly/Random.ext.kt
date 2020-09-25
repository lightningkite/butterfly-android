package com.lightningkite.butterfly

import kotlin.random.Random

fun Random.nextFloat(until: Float): Float = this.nextDouble(until.toDouble()).toFloat()
fun Random.nextFloat(from: Float, until: Float): Float = this.nextDouble(from.toDouble(), until.toDouble()).toFloat()
