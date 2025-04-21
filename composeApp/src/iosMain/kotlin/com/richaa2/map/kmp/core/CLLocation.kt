package com.richaa2.map.kmp.core

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation

@OptIn(ExperimentalForeignApi::class)
fun CLLocation.getLatitude(): Double = this.coordinate().useContents { latitude }

@OptIn(ExperimentalForeignApi::class)
fun CLLocation.getLongitude(): Double = this.coordinate().useContents { longitude }