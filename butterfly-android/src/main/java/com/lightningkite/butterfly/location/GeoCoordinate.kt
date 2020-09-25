//! This file is Khrysalis compatible.
package com.lightningkite.butterfly.location

import android.location.Location
import com.lightningkite.butterfly.Codable

data class GeoCoordinate(val latitude: Double, val longitude: Double): Codable
