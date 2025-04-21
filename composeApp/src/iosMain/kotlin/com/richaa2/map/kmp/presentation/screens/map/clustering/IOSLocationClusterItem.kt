package com.richaa2.map.kmp.presentation.screens.map.clustering

import cocoapods.Google_Maps_iOS_Utils.GMUClusterItemProtocol
import com.richaa2.map.kmp.domain.model.LocationInfo
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class IOSLocationClusterItem(val locationInfo: LocationInfo) : NSObject(), GMUClusterItemProtocol {
    @OptIn(ExperimentalForeignApi::class)
    override fun position(): CValue<CLLocationCoordinate2D> {
        return CLLocationCoordinate2DMake(locationInfo.latitude, locationInfo.longitude)
    }

    @ExperimentalForeignApi
    override fun title(): String? = null

}