package com.richaa2.map.kmp.presentation.screens.map.clustering

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.richaa2.map.kmp.domain.model.LocationInfo

class LocationClusterItem(
    val locationInfo: LocationInfo
    ) : ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(locationInfo.latitude, locationInfo.longitude)
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }

    override fun getZIndex(): Float {
        return 0f
    }
}