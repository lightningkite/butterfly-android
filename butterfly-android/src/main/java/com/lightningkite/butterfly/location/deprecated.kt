package com.lightningkite.butterfly.location

import android.location.Geocoder
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.post
import com.lightningkite.butterfly.views.DialogRequest
import com.lightningkite.butterfly.views.ViewStringRaw
import com.lightningkite.butterfly.views.showDialog


@Deprecated("Use the new RX Style instead")
fun ActivityAccess.geocode(
    coordinate: GeoCoordinate,
    onResult: (List<GeoAddress>) -> Unit
) {
    if (coordinate.latitude == 0.0 && coordinate.longitude == 0.0) {
        onResult(listOf())
        return
    }
    Thread {
        try {
            val result = Geocoder(context)
                .getFromLocation(coordinate.latitude, coordinate.longitude, 1)
            post {
                onResult(result.map { it -> it.toButterfly() })
            }
        } catch (e: Exception) {
            showDialog(DialogRequest(string = ViewStringRaw(e.message ?: "An unknown error occured")))
        }
    }.start()
}

@Deprecated("Use the new RX Style instead")
fun ActivityAccess.geocode(
    address: String,
    onResult: (List<GeoAddress>) -> Unit
) {
    if (address.isEmpty()) {
        onResult(listOf())
        return
    }
    Thread {
        try {
            val result = Geocoder(context)
                .getFromLocationName(address, 1)
            post {
                onResult(result.map { it -> it.toButterfly() })
            }
        } catch (e: Exception) {
            showDialog(ViewStringRaw(e.message ?: "An unknown error occured"))
        }
    }.start()
}