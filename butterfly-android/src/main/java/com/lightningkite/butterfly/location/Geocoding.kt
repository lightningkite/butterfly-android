package com.lightningkite.butterfly.location

import android.location.Geocoder
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

fun ActivityAccess.geocode(
    address: String,
    maxResults: Int = 1
): Single<List<GeoAddress>> {
    if (address.isEmpty()) {
        return Single.just(listOf())
    }
    return Single.create <List<GeoAddress>>{ emitter ->
        Thread {
            try {
                emitter.onSuccess(Geocoder(context)
                    .getFromLocationName(address, maxResults).map { it -> it.toButterfly() })
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }.start()
    }.observeOn(AndroidSchedulers.mainThread())
}

fun ActivityAccess.geocode(
    coordinate: GeoCoordinate,
    maxResults: Int = 1
): Single<List<GeoAddress>> {
    if (coordinate.latitude == 0.0 && coordinate.longitude == 0.0) {
        return Single.just(listOf())
    }
    return Single.create <List<GeoAddress>>{ emitter ->
        Thread {
            try {
                emitter.onSuccess(Geocoder(context)
                    .getFromLocation(coordinate.latitude, coordinate.longitude, maxResults).map { it -> it.toButterfly() })

            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }.start()
    }.observeOn(AndroidSchedulers.mainThread())
}
